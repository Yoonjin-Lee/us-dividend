package com.example.usdividend.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.UserData

@Database(entities = [UserData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}