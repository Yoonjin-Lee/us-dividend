package com.example.usdividend.view.input

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.local.dao.CompanyDao
import com.example.usdividend.data.local.dao.StockDao
import com.example.usdividend.data.local.dao.UserDao
import com.example.usdividend.data.type.CompanyData
import com.example.usdividend.data.type.StockData
import com.example.usdividend.view.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StockInputViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stockDao: StockDao,
    private val userDao: UserDao,
    private val companyDao: CompanyDao
) : ViewModel() {
    fun insert(stockData: StockData) {
        CoroutineScope(Dispatchers.IO).launch {
            stockDao.insertAll(
                stockData
            )
        }
    }

    fun insertCompany(companyData: CompanyData){
        CoroutineScope(Dispatchers.IO).launch{
            companyDao.insertAll(companyData)
        }
    }

    fun updateHoldingdollar(price: String, quantity: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val newDollar =
                userDao.getHoldingdollar() + price.toFloat() * quantity.toFloat()
            userDao.updateHoldingdollar(newDollar)
        }
    }

    fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun finish(){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, intent, null)
    }
}