package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.model.HabitItem

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM habits WHERE id IN (:habitIds)")
    fun loadAllByIds(habitIds: IntArray): List<HabitItem>

    @Query("SELECT * FROM habits WHERE id LIKE :id LIMIT 1")
    fun findById(id: Long): HabitItem?

    @Query("SELECT * FROM habits WHERE type LIKE :type")
    fun filterByType(type: Type): List<HabitItem>

    @Query("SELECT * FROM habits WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): List<HabitItem>

    @Query(
        "SELECT * FROM habits " +
                "WHERE name LIKE '%' || :name || '%' " +
                "ORDER BY " +
                "CASE WHEN :sortBy = 'ASCENDING' THEN name END ASC, " +
                "CASE WHEN :sortBy = 'DESCENDING' THEN name END DESC "
    )
    fun filterAndSort(name: String, sortBy: SortBy): List<HabitItem>

    @Insert
    fun insertAll(vararg habits: HabitItem)

    @Insert
    fun insert(habit: HabitItem)

    @Delete
    fun delete(habit: HabitItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(habit: HabitItem)
}