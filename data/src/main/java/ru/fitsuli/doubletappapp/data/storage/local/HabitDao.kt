package ru.fitsuli.doubletappapp.data.storage.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.domain.models.SortBy
import ru.fitsuli.doubletappapp.domain.models.Type

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getOnce(): List<HabitData>

    @Query("SELECT * FROM habits WHERE id IN (:habitIds)")
    suspend fun loadAllByIds(habitIds: IntArray): List<HabitData>

    @Query("SELECT * FROM habits WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: String): HabitData?

    @Query("SELECT * FROM habits WHERE type LIKE :type")
    suspend fun filterByType(type: Type): List<HabitData>

    @Query("SELECT * FROM habits WHERE name LIKE '%' || :name || '%'")
    suspend fun filterByName(name: String): List<HabitData>

    @Query(
        "SELECT * FROM habits " +
                "WHERE name LIKE '%' || :name || '%' " +
                "ORDER BY " +
                "CASE WHEN :sortBy = 'ASCENDING' THEN name END ASC, " +
                "CASE WHEN :sortBy = 'DESCENDING' THEN name END DESC "
    )
    fun getFilteredAndSorted(name: String, sortBy: SortBy): Flow<List<HabitData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg habits: HabitData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: HabitData)

    @Delete
    suspend fun delete(habit: HabitData)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(habit: HabitData)
}