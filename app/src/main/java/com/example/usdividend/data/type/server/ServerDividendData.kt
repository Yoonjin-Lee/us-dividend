package com.example.usdividend.data.type.server

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ServerDividendData(
    @SerializedName("holdingId") val holdingId : Int,
    @SerializedName("userid") val userid : Int,
    @SerializedName("stockid") val stockid : Int,
    @SerializedName("stockname") val stockname : String,
    @SerializedName("price") val price : Float,
    @SerializedName("exrate") val exrate : Float,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("checkedAt") val checkedAt : Timestamp?,
    @SerializedName("deletedAt") val deletedAt : Timestamp?
)