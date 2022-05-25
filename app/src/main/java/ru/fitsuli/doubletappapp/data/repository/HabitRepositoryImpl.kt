package ru.fitsuli.doubletappapp.data.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.data.storage.local.LocalDataSource
import ru.fitsuli.doubletappapp.data.storage.network.RemoteDataSource
import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter
import java.time.OffsetDateTime

class HabitRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : HabitRepository {
    override fun getHabits() = local.getAll()

    override suspend fun getFilteredHabits(filter: SearchSortFilter) =
        local.getFilteredSorted(filter)

    override suspend fun getById(id: String): HabitItem? {
        return local.getById(id)
    }

    override suspend fun add(habit: HabitItem) {
        remote.add(habit)?.let { uid ->
            local.add(habit.copy(id = uid.uid))
        } ?: local.add(habit.copy(isUploadPending = true))
    }

    override suspend fun update(habit: HabitItem) = withContext(IO) {
        remote.update(habit)?.let {
            local.update(habit)
        } ?: local.update(habit.copy(isUpdatePending = true))
    }

    override suspend fun delete(habit: HabitItem): Unit = withContext(IO) {
        remote.delete(habit)?.let {
            local.delete(habit)
        }
    }

    override suspend fun markAsDone(habit: HabitItem) = withContext(IO) {
        val newDoneDates = habit.doneDates + OffsetDateTime.now()
        remote.markAsDone(habit)?.let {
            local.update(
                habit.copy(
                    count = habit.count + 1,
                    doneDates = newDoneDates
                )
            )
        } ?: local.update(
            habit.copy(
                count = habit.count + 1,
                doneDates = newDoneDates,
                isUpdatePending = true
            )
        )
    }

    // upload pending operations
    override suspend fun actualizePending() = withContext(IO) {
        getHabits().singleOrNull()?.forEach { habit ->
            if (habit.isUploadPending) {
                remote.add(habit)?.let { uid ->
                    local.delete(habit)
                    local.add(habit.copy(id = uid.uid, isUpdatePending = false))
                }

                return@forEach
            }

            if (habit.isUpdatePending) {
                remote.update(habit)?.let {
                    local.update(habit.copy(isUploadPending = false))
                }

                return@forEach
            }
        }
    }

    // reload everything from the remote and apply to the local
    override suspend fun actualizeDiff() = withContext(IO) {
        remote.getAll()?.let {
            local.deleteAll()
            local.addAll(it)
        }
    }
}