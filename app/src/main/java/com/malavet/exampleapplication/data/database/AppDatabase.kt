package com.malavet.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.malavet.exampleapplication.data.database.DataDao

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
@TypeConverters(HobbiesListConverter::class)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}