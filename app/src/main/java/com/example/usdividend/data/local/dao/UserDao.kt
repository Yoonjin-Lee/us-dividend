package com.example.usdividend.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.usdividend.data.type.UserData

@Dao
interface UserDao {
    @Insert
    fun insertAll(vararg userData: UserData)

    // 전체 삭제
    @Query("DELETE FROM USERDATA")
    fun deleteAll()

    // 전체 데이터 개수
    @Query("SELECT COUNT(user_id) FROM USERDATA")
    fun countAll() : Int

    //user_name으로 user_email 찾기
    @Query("SELECT user_email FROM userdata WHERE user_name = :userName")
    fun findEmailByName(userName : String) : String

    //user_email
    @Query("SELECT user_name FROM userdata WHERE user_email = :userEmail")
    fun findNameByEmail(userEmail : String) : String
}