package com.example.usdividend.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application(){
    companion object{
        const val BASE_URL = "https://port-0-dividendapi-7e6o2clhvg1iyw.sel4.cloudtype.app"
    }
}