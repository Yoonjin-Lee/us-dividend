package com.example.usdividend.view.setting

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.view.login.LoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDao: UserDao
) : ViewModel(){
    private val _nickname = MutableLiveData<String>()
    val nickname : LiveData<String> get() = _nickname

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> get() = _email

    fun getNickname() = CoroutineScope(Dispatchers.IO).launch{
        _nickname.postValue(userDao.getName(0))
    }

    fun getEmail() = CoroutineScope(Dispatchers.IO).launch {
        _email.postValue(userDao.getEmail(0))
    }

    fun logout(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        ContextCompat.startActivity(context, intent, null)
    }

    init {
        getNickname()
        getEmail()
    }
}