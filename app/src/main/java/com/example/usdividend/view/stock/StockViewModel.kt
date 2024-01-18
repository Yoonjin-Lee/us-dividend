package com.example.usdividend.view.stock

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usdividend.view.input.StockInputActivity
import com.example.usdividend.data.local.dao.StockDao
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
    private val stockDao: StockDao
) : ViewModel() {
    private val _stockList = MutableLiveData<List<StockData>>()
    val stockList : LiveData<List<StockData>> get() =  _stockList

    private fun getStockList() = CoroutineScope(Dispatchers.IO).launch {
        _stockList.postValue(stockDao.getAllStockData())
    }

    fun move(){
        val intent = Intent(context, StockInputActivity::class.java)
        startActivity(context, intent, null)
    }

    fun showToast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    init {
        getStockList()
    }
}