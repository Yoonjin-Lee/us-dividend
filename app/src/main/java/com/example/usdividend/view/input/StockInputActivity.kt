package com.example.usdividend.view.input

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.usdividend.ui.theme.UsDividendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockInputActivity : ComponentActivity() {
    private val stockInputViewModel : StockInputViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                StockInputView(stockInputViewModel)
            }
        }
    }
}