package com.example.usdividend.view.input

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.usdividend.view.input.StockInputScreen
import com.example.usdividend.ui.theme.UsDividendTheme

class StockInputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                StockInputScreen(this)
            }
        }
    }
}