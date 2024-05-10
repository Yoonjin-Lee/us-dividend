package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DividendData (
    @ColumnInfo("user_id") val userId : Int,
    @ColumnInfo("stock_name") val stockName : String,
    @ColumnInfo("stock_dividend") val stockDividend : String,
    @ColumnInfo("month") val month : String
){
    @PrimaryKey(autoGenerate = true)
    var idx = 0
}