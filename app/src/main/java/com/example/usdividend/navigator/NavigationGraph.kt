package com.example.usdividend.navigator

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.usdividend.DividendScreen
import com.example.usdividend.SettingView
import com.example.usdividend.view.dividend.DividendViewModel
import com.example.usdividend.view.main.MainViewModel
import com.example.usdividend.view.setting.SettingViewModel
import com.example.usdividend.view.stock.StockView
import com.example.usdividend.view.stock.StockViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    context: Context,
    stockViewModel: StockViewModel,
    dividendViewModel: DividendViewModel,
    settingViewModel: SettingViewModel
) {
    NavHost(navController = navController, startDestination = "stockScreen") {
        composable("dividendScreen") {
            DividendScreen(context)
        }
        composable("stockScreen") {
            StockView(stockViewModel)
        }
        composable("settingScreen") {
            SettingView(context)
        }
    }
}