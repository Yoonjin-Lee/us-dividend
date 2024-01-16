package com.example.usdividend.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.usdividend.MainScreen
import com.example.usdividend.ui.theme.UsDividendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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