package com.example.usdividend

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.usdividend.data.type.DividendListData
import com.example.usdividend.data.type.StockIdData

class SharedViewModel : ViewModel() {
    var myVariable by mutableStateOf(false)
    var myCompany by mutableStateOf("")

//    var dollars by mutableStateOf(holdingDollars)
}

var companyList = mutableStateListOf<String>()

var stockIdList = mutableStateListOf<StockIdData>()

var dividendList = mutableStateListOf<DividendListData>()
