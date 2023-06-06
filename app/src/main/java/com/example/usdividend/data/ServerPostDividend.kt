package com.example.usdividend.data

data class ServerPostDividend (
    val holdingId : Int,
    val userId : Int,
    val stockId : Int,
    val quantity : Int
)