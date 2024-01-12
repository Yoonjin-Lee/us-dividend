package com.example.usdividend.server

import com.example.usdividend.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //로그인
    @POST("/users/login")
    fun signUp(
        @Body nameEmailData: NameEmailData
    ): Call<String>

    //로그아웃
    @PATCH("/users/{userId}")
    fun logout(
        @Path("userId") userid: Int
    ): Call<String>

    //POST는 무조건 Body로 보내야 함!!
    //보유 주식 등록
    @POST("/holdings")
    fun postStock(
        @Body serverPostStock: ServerPostStock
    ): Call<String>

    //주식 이름으로 아이디 받기
    //주식 정보
    @GET("/stocks/name/{name}")
    fun getStockId(
        @Path("name") name: String
    ): Call<Int>

    //주식 리스트 받기
    @GET("/holdings/{userid}")
    fun getStockList(
        @Path("userid") userid: Int
    ): Call<String>

    //배당금 로그 전송
    @POST("/dividends")
    fun postDividend(
        @Body serverPostDividend: ServerPostDividend
    ): Call<String>

    //배당금 로그 조회
    @GET("/dividends/{userId}")
    fun getDividendHistory(
        @Path("userId") userId: Int
    ): Call<String>

    //보유 달러 받기
    @GET("/users/{userid}")
    fun getDollars(
        @Path("userid") userid: Int
    ): Call<String>

    //환율 받기
    @GET("/users/exchange-rate")
    fun getExchange(): Call<String>
}