package ru.fitsuli.doubletappapp.data.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.data.storage.local.LocalDataSource
import ru.fitsuli.doubletappapp.data.storage.network.RemoteDataSource
import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitDomain
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter
import java.time.OffsetDateTime

class HabitRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : HabitRepository {
    private val sortFlow = MutableStateFlow(SearchSortFilter())

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFilteredHabits() = sortFlow
        .flatMapLatest { filter ->
            local.getFilteredSorted(filter)
                .map { // check me please
                    buildList {
                        it.forEach {
                            add(it.toDomainHabit())
                        }
                    }
                }
        }

    override fun setFilterBy(filter: SearchSortFilter) {
        sortFlow.value = filter
    }

    override suspend fun getById(id: String): HabitDomain? {
        return local.getById(id)?.toDomainHabit()
    }

    override suspend fun add(habit: HabitDomain) {
        val habitData = habit.toDataHabit()

        remote.add(habitData)?.let { uid ->
            local.add(habitData.copy(id = uid.uid))
        } ?: local.add(habitData.copy(isUploadPending = true))
    }

    override suspend fun update(habit: HabitDomain) = withContext(IO) {
        val habitData = habit.toDataHabit()

        remote.update(habitData)?.let {
            local.update(habitData)
        } ?: local.update(habitData.copy(isUpdatePending = true))
    }

    override suspend fun delete(habit: HabitDomain): Unit = withContext(IO) {
        val habitData = habit.toDataHabit()

        remote.delete(habitData)?.let {
            local.delete(habitData)
        }
    }

    override suspend fun markAsDone(habit: HabitDomain) = withContext(IO) {
        val habitData = habit.toDataHabit()

        val now = OffsetDateTime.now()
        val newDoneDates = habitData.doneDates + now
        remote.markAsDone(habitData)?.let {
            remote.update(
                habitData.copy(
                    modifiedDate = now,
                    doneDates = newDoneDates,
                )
            )?.let {
                local.update(
                    habitData.copy(
                        modifiedDate = now,
                        doneDates = newDoneDates
                    )
                )
            } ?: local.update(
                habitData.copy(
                    modifiedDate = now,
                    doneDates = newDoneDates,
                    isUpdatePending = true
                )
            )
        } ?: local.update(
            habitData.copy(
                modifiedDate = now,
                doneDates = newDoneDates,
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

    private fun HabitDomain.toDataHabit() = HabitData(
        name,
        description,
        priority,
        type,
        period,
        goalCount,
        srgbColor,
        modifiedDate,
        id,
        doneDates,
        isUploadPending,
        isUpdatePending
    )

    private fun HabitData.toDomainHabit() = HabitDomain(
        name,
        description,
        priority,
        type,
        period,
        goalCount,
        srgbColor,
        modifiedDate,
        id,
        doneDates,
        isUploadPending,
        isUpdatePending
    )
}