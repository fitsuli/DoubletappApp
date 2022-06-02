package ru.fitsuli.doubletappapp.data.storage.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fitsuli.doubletappapp.data.models.HabitData

@Database(entities = [HabitData::class], version = 9)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        private var dbInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (dbInstance == null)
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return dbInstance!!
        }
    }
}