package ru.fitsuli.doubletappapp.data.storage.network

import com.haroldadmin.cnradapter.invoke
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitDoneBody
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitUid

class NetworkStorage : RemoteDataSource {
    private val habitApi = RetrofitRequestApi.instance

    override suspend fun getAll(): List<HabitData>? {
        return executeWithConfiguredRetry {
            habitApi.getAll(AUTH_TOKEN)
        }()
    }

    override suspend fun add(
        habit: HabitData
    ): HabitUid? {
        return executeWithConfiguredRetry {
            habitApi.add(
                AUTH_TOKEN, habit.copy(id = "") // required by the API
            )
        }()
    }

    override suspend fun addAll(items: List<HabitData>): Unit? {
        return items.forEach {
            add(it) ?: return null // error indicator
        }
    }

    override suspend fun update(
        habit: HabitData
    ): HabitUid? {
        return executeWithConfiguredRetry { habitApi.add(AUTH_TOKEN, habit) }()
    }

    override suspend fun delete(
        habit: HabitData
    ): Unit? {
        return executeWithConfiguredRetry { habitApi.delete(AUTH_TOKEN, HabitUid(habit.id)) }()
    }

    override suspend fun deleteAll(): Unit? {
        return getAll()?.let { list ->
            list.forEach { delete(it) ?: return@let null }
        }
    }

    override suspend fun markAsDone(habit: HabitData): Unit? {
        return executeWithConfiguredRetry {
            habitApi.markAsDone(
                AUTH_TOKEN,
                HabitDoneBody(date = habit.modifiedDate, habitUid = habit.id)
            )
        }()
    }
}