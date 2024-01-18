package com.example.usdividend.view.input

import androidx.lifecycle.ViewModel
import com.example.usdividend.data.local.dao.StockDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockInputViewModel @Inject constructor(
    private val stockDao: StockDao
) : ViewModel(){

}