package com.example.myapplicationmeuprimeiroapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProjectEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}