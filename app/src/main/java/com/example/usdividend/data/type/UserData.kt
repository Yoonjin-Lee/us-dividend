package com.example.usdividend.data.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @ColumnInfo(name = "user_id") val userId : Int,
    @ColumnInfo(name = "user_name") val userName : String,
    @ColumnInfo(name = "user_email") val email : String,
    @ColumnInfo(name = "holding_dollar") val holdingDollar : Float
){
    @PrimaryKey(autoGenerate = true)
    var idx : Int = 0
}