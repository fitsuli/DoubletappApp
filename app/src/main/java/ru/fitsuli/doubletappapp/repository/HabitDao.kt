package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.model.HabitItem

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<HabitItem>>

    @Query("SELECT * FROM habits WHERE id IN (:habitIds)")
    fun loadAllByIds(habitIds: IntArray): List<HabitItem>

    @Query("SELECT * FROM habits WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): HabitItem

    @Query("SELECT * FROM habits WHERE name LIKE '%' || :name || '%'")
    fun filterByNameList(name: String): List<HabitItem>

    @Query("SELECT * FROM habits WHERE type LIKE :type LIMIT 1")
    fun findByType(type: Type): HabitItem

    @Insert
    fun insertAll(vararg habits: HabitItem)

    @Insert
    fun insert(habit: HabitItem)

    @Delete
    fun delete(habit: HabitItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(habit: HabitItem)
}