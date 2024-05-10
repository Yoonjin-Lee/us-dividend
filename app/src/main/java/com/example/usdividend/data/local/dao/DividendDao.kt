package com.example.usdividend.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.usdividend.data.type.DividendData

@Dao
interface DividendDao {
    @Insert
    fun insertAll(vararg dividendData: DividendData)

    @Query("SELECT * FROM DividendData")
    fun getAllDividend() : List<DividendData>
}