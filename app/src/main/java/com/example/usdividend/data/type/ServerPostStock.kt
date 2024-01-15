package com.example.usdividend.data.type

data class ServerPostStock(
    val userId: Int,
    val stockName: String,
    val price: Float,
    val exchangeRate: Float,
    val quantity: Int
)

