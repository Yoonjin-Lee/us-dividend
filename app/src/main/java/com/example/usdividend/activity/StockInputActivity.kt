package com.example.usdividend.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.usdividend.screen.Name
import com.example.usdividend.screen.StockInputScreen
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