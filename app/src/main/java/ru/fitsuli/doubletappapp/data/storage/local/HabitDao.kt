package ru.fitsuli.doubletappapp.data.storage.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SortBy
import ru.fitsuli.doubletappapp.domain.models.Type

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitItem>>

    @Query("SELECT * FROM habits WHERE id IN (:habitIds)")
    suspend fun loadAllByIds(habitIds: IntArray): List<HabitItem>

    @Query("SELECT * FROM habits WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: String): HabitItem?

    @Query("SELECT * FROM habits WHERE type LIKE :type")
    suspend fun filterByType(type: Type): List<HabitItem>

    @Query("SELECT * FROM habits WHERE name LIKE '%' || :name || '%'")
    suspend fun filterByName(name: String): List<HabitItem>

    @Query(
        "SELECT * FROM habits " +
                "WHERE name LIKE '%' || :name || '%' " +
                "ORDER BY " +
                "CASE WHEN :sortBy = 'ASCENDING' THEN name END ASC, " +
                "CASE WHEN :sortBy = 'DESCENDING' THEN name END DESC "
    )
    suspend fun filterAndSort(name: String, sortBy: SortBy): List<HabitItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg habits: HabitItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: HabitItem)

    @Delete
    suspend fun delete(habit: HabitItem)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(habit: HabitItem)
}