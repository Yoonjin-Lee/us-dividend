package com.example.usdividend.data.repository

import androidx.annotation.WorkerThread
import com.example.usdividend.data.type.NameEmailData
import com.example.usdividend.di.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginRepository @Inject constructor(private val apiService: ApiService) {
    @WorkerThread
    suspend fun signUp(nameEmailData: NameEmailData) = apiService.signUp(nameEmailData)
}