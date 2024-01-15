package com.example.usdividend.di

import android.content.Context
import androidx.room.Room
import com.example.usdividend.R
import com.example.usdividend.data.local.AppDatabase
import com.example.usdividend.data.local.dao.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    )=
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.app_name)
        ).build()

    @Provides
    @Singleton
    fun provideUserDAO(database: AppDatabase) : UserDao = database.userDao()
}