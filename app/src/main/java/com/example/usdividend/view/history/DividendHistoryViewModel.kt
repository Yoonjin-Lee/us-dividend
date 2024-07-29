package com.example.usdividend.view.history

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.local.dao.DividendDao
import com.example.usdividend.data.type.DividendData
import com.example.usdividend.data.type.DividendListData
import com.example.usdividend.function.excel
import com.example.usdividend.view.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DividendHistoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dividendDao: DividendDao
) : ViewModel(){
    private val _dividendData = MutableLiveData<List<DividendData>>()
    val dividendData : LiveData<List<DividendData>> get() = _dividendData

    fun getDividendData() = CoroutineScope(Dispatchers.IO).launch {
        _dividendData.postValue(dividendDao.getAllDividend())
    }
    fun makeExcel(){
        val dividendAll = dividendDao.getAllDividend()
        val dividendList = ArrayList<DividendListData>()
        for(dividend in dividendAll){
            dividendList.add(
                DividendListData(
                    dividend.stockName,
                    dividend.stockDividend.toFloat(),
                    dividend.month.toInt()
                )
            )
        }
        excel(context, dividendList)
    }

    fun showToast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun goBack(){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, intent, null)
    }

    init {
        getDividendData()
    }
}