package com.example.usdividend

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.DividendListData
import com.example.usdividend.data.StockIdData
import com.example.usdividend.server.ApiService
import com.example.usdividend.server.getRetrofit

class SharedViewModel : ViewModel() {
    var myVariable by mutableStateOf(false)
    var myCompany by mutableStateOf("")
}

var companyList = mutableStateListOf<String>()

var stockIdList = mutableStateListOf<StockIdData>()

var dividendList = mutableStateListOf<DividendListData>()

val authService = getRetrofit().create(ApiService::class.java)
