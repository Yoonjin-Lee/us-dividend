package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockIdData(
    @ColumnInfo(name = "stock_name") val stockName : String,
    @ColumnInfo(name = "stock_id") val stockId : Int,
    @ColumnInfo(name = "stock_quantity") val quantity : Int
){
    @PrimaryKey(autoGenerate = true)
    var idx : Int = 0
}

