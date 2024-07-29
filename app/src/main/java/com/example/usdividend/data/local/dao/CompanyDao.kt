package com.example.usdividend.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.usdividend.data.type.CompanyData

@Dao
interface CompanyDao {
    @Insert
    fun insertAll(companyData: CompanyData)

    @Query("SELECT company FROM CompanyData")
    fun getCompany() : List<String>

    // 전체 삭제
    @Query("DELETE FROM CompanyData")
    fun deleteAll()

    @Query("DELETE FROM CompanyData WHERE company = :company")
    fun deleteCompany(company : String)
}