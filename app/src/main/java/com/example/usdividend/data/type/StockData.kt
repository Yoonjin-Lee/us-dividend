package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockData (
    @PrimaryKey val userId : Int,
    @ColumnInfo(name = "stock_name") val stockName : String,
    @ColumnInfo(name = "stock_quantity") val stockQuantity : String,
    @ColumnInfo(name = "exchange") val exchange : String,
    @ColumnInfo(name = "stock_price") val stockPrice : String,
    @ColumnInfo(name = "stock_dividend") val stockDividend : String
)