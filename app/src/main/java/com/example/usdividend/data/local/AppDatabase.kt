package com.example.usdividend.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usdividend.data.local.dao.CompanyDao
import com.example.usdividend.data.local.dao.DividendDao
import com.example.usdividend.data.local.dao.StockDao
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.CompanyData
import com.example.usdividend.data.type.DividendData
import com.example.usdividend.data.type.StockData
import com.example.usdividend.data.type.UserData

@Database(entities = [UserData::class, StockData::class, DividendData::class, CompanyData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun stockDao() : StockDao
    abstract fun dividendDao() : DividendDao

    abstract fun companyDao() : CompanyDao
}