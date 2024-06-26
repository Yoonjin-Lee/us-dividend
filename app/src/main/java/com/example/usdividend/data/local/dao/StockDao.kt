package com.example.usdividend.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.usdividend.data.type.StockData

@Dao
interface StockDao{
    @Insert
    fun insertAll(vararg stockData: StockData)

    // 전체 주식 리스트 가져 오기
    @Query("SELECT * FROM STOCKDATA")
    fun getAllStockData() : List<StockData>

    // 전체 주식 이름 리스트 가져 오기
    @Query("SELECT stock_name FROM STOCKDATA")
    fun getAllStockName() : List<String>

    // 주식 이름으로 배당금 찾기
    @Query("SELECT stock_dividend FROM STOCKDATA WHERE stock_name = :stockName")
    fun findDividendByName(stockName: String) : String
}