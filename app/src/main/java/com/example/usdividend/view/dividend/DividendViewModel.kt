package com.example.usdividend.view.dividend

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.local.dao.DividendDao
import com.example.usdividend.data.local.dao.StockDao
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.DividendData
import androidx.compose.runtime.*
import com.example.usdividend.data.local.dao.CompanyDao
import com.example.usdividend.data.type.CompanyData
import com.example.usdividend.view.history.DividendHistoryActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DividendViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val userDao: UserDao,
    private val stockDao: StockDao,
    private val dividendDao: DividendDao,
    private val companyDao: CompanyDao
) : ViewModel(){
    private val _dollar = MutableLiveData<String>()
    val dollar : LiveData<String> get() = _dollar

    private val _stockNameList = MutableLiveData<List<String>>()
    val stockNameList : LiveData<List<String>> get() =  _stockNameList

    private val _dividendList = MutableLiveData<List<DividendData>>()
    val dividendList : LiveData<List<DividendData>> get() =  _dividendList

    private val _month = MutableLiveData<String>()
    val month : LiveData<String> get() = _month

    var justOneTime = mutableStateOf(true)

    fun getDollar() = CoroutineScope(Dispatchers.IO).launch {
        _dollar.postValue(userDao.getHoldingdollar().toString())
    }

    fun getStockNameList() = CoroutineScope(Dispatchers.IO).launch {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DATE)
        Log.d("day", day.toString())
        // 매월 1일에 주식 목록 리스트를 갱신
        if (day == 1){
            companyDao.deleteAll()
            val newList = stockDao.getAllStockName()
            for(new in newList){
                companyDao.insertAll(
                    CompanyData(
                        new
                    )
                )
            }
        }else{
            _stockNameList.postValue(companyDao.getCompany())
        }
    }

    private fun getDividendList() = CoroutineScope(Dispatchers.IO).launch {
        _dividendList.postValue(dividendDao.getAllDividend())
    }

    private fun getMonth() = CoroutineScope(Dispatchers.IO).launch {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        _month.postValue(currentMonth.toString())
    }

    fun move(){
        val intent = Intent(context, DividendHistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    /*********dividend*********/
    fun insertDividend(
        stockName: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        val dividend = stockDao.findDividendByName(stockName)
        if (dividend.isNotEmpty()){
            dividendDao.insertAll(
                DividendData(
                    0,
                    stockName,
                    dividend,
                    month.value!!
                )
            )
            Log.d("room", "배당금 삽입")
        }
    }

    fun updateHoldingdollar(stockName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dividend = stockDao.findDividendByName(stockName)
            val newDollar =
                userDao.getHoldingdollar() + dividend.toFloat()
            userDao.updateHoldingdollar(newDollar)
        }
    }

    fun deleteCompany(company : String){
        CoroutineScope(Dispatchers.IO).launch {
            companyDao.deleteCompany(company)
            Log.d("room", "목록 삭제 : $company")
            getStockNameList()
        }
    }

    init {
        getDollar()
        getStockNameList()
        getDividendList()
        getMonth()
    }
}