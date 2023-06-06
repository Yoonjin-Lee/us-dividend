package com.example.usdividend.server

import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager

const val BASE_URL = "https://port-0-dividendapi-7e6o2clhvg1iyw.sel4.cloudtype.app"
private var instance: Retrofit? = null
private val gson = GsonBuilder().setLenient().create()

fun getRetrofit(): Retrofit {
    if (instance == null) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .addInterceptor(interceptor)
            .build()

        val gson = GsonBuilder().setLenient().create()

        instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    return instance!!
}
