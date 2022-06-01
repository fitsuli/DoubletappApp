package ru.fitsuli.doubletappapp.data.storage.network

import com.haroldadmin.cnradapter.invoke
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitDoneBody
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitUid

class NetworkStorage : RemoteDataSource {
    private val habitApi = RetrofitRequestApi.getInstance()

    override suspend fun getAll(): List<HabitData>? =
        withContext(IO) {
            executeWithConfiguredRetry {
                habitApi.getAll(AUTH_TOKEN)
            }()
        }

    override suspend fun add(
        habit: HabitData
    ): HabitUid? =
        withContext(IO) {
            executeWithConfiguredRetry {
                habitApi.add(
                    AUTH_TOKEN, habit.copy(id = "") // required by the API
                )
            }()
        }

    override suspend fun addAll(items: List<HabitData>): Unit? = withContext(IO) {
        items.forEach {
            add(it) ?: return@withContext null // as error indicator
        }
    }

    override suspend fun update(
        habit: HabitData
    ): HabitUid? =
        withContext(IO) {
            executeWithConfiguredRetry { habitApi.add(AUTH_TOKEN, habit) }()
        }

    override suspend fun delete(
        habit: HabitData
    ): Unit? =
        withContext(IO) {
            executeWithConfiguredRetry { habitApi.delete(AUTH_TOKEN, HabitUid(habit.id)) }()
        }

    override suspend fun deleteAll() = withContext(IO) {
        getAll()?.let { list ->
            list.forEach { delete(it) ?: return@withContext null }
        }
    }

    override suspend fun markAsDone(habit: HabitData): Unit? = withContext(IO) {
        executeWithConfiguredRetry {
            habitApi.markAsDone(
                AUTH_TOKEN,
                HabitDoneBody(date = habit.modifiedDate, habitUid = habit.id)
            )
        }()
    }
}