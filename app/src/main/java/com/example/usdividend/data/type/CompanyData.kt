package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyData(
    @ColumnInfo("company") val company : String
){
    @PrimaryKey(autoGenerate = true)
    var idx = 0
}
