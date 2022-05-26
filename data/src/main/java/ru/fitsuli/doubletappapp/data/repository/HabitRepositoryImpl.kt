package ru.fitsuli.doubletappapp.data.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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
    private val sortFlow = MutableStateFlow(SearchSortFilter())

    override fun getHabits() = local.getAll()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFilteredHabits() = sortFlow
        .flatMapLatest {
            local.getFilteredSorted(it)
        }

    override fun setFilterBy(filter: SearchSortFilter) {
        sortFlow.value = filter
    }

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
        val now = OffsetDateTime.now()
        val newDoneDates = habit.doneDates + now
        remote.markAsDone(habit)?.let {
            remote.update(
                habit.copy(
                    count = habit.count + 1
                )
            )?.let {
                local.update(
                    habit.copy(
                        count = habit.count + 1,
                        modifiedDate = now,
                        doneDates = newDoneDates
                    )
                )
            } ?: local.update(
                habit.copy(
                    count = habit.count + 1,
                    modifiedDate = now,
                    doneDates = newDoneDates,
                    isUpdatePending = true
                )
            )
        } ?: local.update(
            habit.copy(
                count = habit.count + 1,
                doneDates = newDoneDates,
                modifiedDate = now,
                isUpdatePending = true
            )
        )
    }

    // upload pending operations
    override suspend fun actualizePending() = withContext(IO) {
        local.getOnce().forEach { habit ->
            if (habit.isUploadPending) {
                remote.add(habit)?.let { uid ->
                    local.delete(habit)
                    local.add(habit.copy(id = uid.uid, isUpdatePending = false))
                } ?: return@withContext // error

                return@forEach
            }

            if (habit.isUpdatePending) {
                remote.update(habit)?.let {
                    local.update(habit.copy(isUploadPending = false))
                } ?: return@withContext // error

                return@forEach
            }
        }

        // reload everything from the remote and apply to the local
        remote.getAll()?.let {
            local.deleteAll()
            local.addAll(it)
        }
    }
}