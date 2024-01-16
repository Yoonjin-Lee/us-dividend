package com.example.usdividend.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.usdividend.ui.theme.UsDividendTheme
import com.example.usdividend.view.dividend.DividendViewModel
import com.example.usdividend.view.setting.SettingViewModel
import com.example.usdividend.view.stock.StockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val stockViewModel : StockViewModel by viewModels()
    private val dividendViewModel : DividendViewModel by viewModels()
    private val settingViewModel : SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                MainView(
                    stockViewModel,
                    dividendViewModel,
                    settingViewModel,
                    this)
            }
        }
    }
}