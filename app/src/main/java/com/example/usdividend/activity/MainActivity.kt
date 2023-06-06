package com.example.usdividend.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.usdividend.MainScreen
import com.example.usdividend.ui.theme.UsDividendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                MainScreen(this)
            }
        }
    }
}