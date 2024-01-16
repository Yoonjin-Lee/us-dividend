package com.example.usdividend.data.repository

import androidx.annotation.WorkerThread
import com.example.usdividend.data.type.server.ServerNameEmailData
import com.example.usdividend.di.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun signUp(serverNameEmailData: ServerNameEmailData) = apiService.signUp(serverNameEmailData)
}