package com.example.usdividend.view.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.usdividend.ui.theme.UsDividendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DividendHistoryActivity : ComponentActivity() {
    private val dividendHistoryViewModel : DividendHistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                DividendHistoryView(dividendHistoryViewModel)
            }
        }
    }
}