package ru.fitsuli.doubletappapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.fitsuli.doubletappapp.model.HabitItem

private const val DB_NAME = "habits"

@Database(entities = [HabitItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        private var dbInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (dbInstance == null) dbInstance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DB_NAME
            )
                .allowMainThreadQueries() // TODO: get rid of
                .fallbackToDestructiveMigration()
                .build()

            return dbInstance!!
        }
    }
}