package com.example.usdividend.view.stock

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usdividend.view.input.StockInputActivity
import com.example.usdividend.data.local.dao.StockDao
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.StockData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stockDao: StockDao,
    private val userDao: UserDao
) : ViewModel() {
    private val _stockList = MutableLiveData<List<StockData>>()
    val stockList : LiveData<List<StockData>> get() =  _stockList

    private val _dollar = MutableLiveData<String>()
    val dollar : LiveData<String> get() = _dollar

    private fun getStockList() = CoroutineScope(Dispatchers.IO).launch {
        _stockList.postValue(stockDao.getAllStockData())
    }
    fun getDollar() = CoroutineScope(Dispatchers.IO).launch {
        _dollar.postValue(userDao.getHoldingdollar().toString())
        Log.i("room", "${userDao.getHoldingdollar()}")
    }

    fun move(){
        val intent = Intent(context, StockInputActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    fun showToast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    init {
        getStockList()
        getDollar()
    }
}