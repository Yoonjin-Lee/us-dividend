package com.example.usdividend.data.repository

import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.UserData
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun insertAll(userData: UserData){
        return userDao.insertAll(userData)
    }

    fun deleteAll(){
        return userDao.deleteAll()
    }

    fun countAll() : Int{
        return userDao.countAll()
    }

    fun findEmailByName(userName : String) : String{
        return userDao.findEmailByName(userName)
    }

    fun findNameByEmail(userEmail : String) : String{
        return  userDao.findNameByEmail(userEmail)
    }
}