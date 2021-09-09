package com.android.example.pdp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.example.pdp.dao.PdpDao
import com.android.example.pdp.models.Course
import com.android.example.pdp.models.Group
import com.android.example.pdp.models.Mentor
import com.android.example.pdp.models.Student

@Database(entities = [Course::class, Mentor::class, Group::class, Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pdpDao(): PdpDao

    companion object {
        private var appDatabase: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}