package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DividendData (
    @PrimaryKey val userId : Int,
    @ColumnInfo("stock_name") val stockName : String,
    @ColumnInfo("stock_dividend") val stockDividend : String,
    @ColumnInfo("month") val month : String
)