package com.example.usdividend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.usdividend.screen.DividendHistoryScreen
import com.example.usdividend.ui.theme.UsDividendTheme

class DividendHistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                DividendHistoryScreen(this)
            }
        }
    }
}