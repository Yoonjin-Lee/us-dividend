package com.example.usdividend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.usdividend.MainScreen
import com.example.usdividend.StockInputActivity
import com.example.usdividend.screen.StockInputScreen
import com.example.usdividend.screen.StockScreen

//@Composable
//fun StockScreenNavHost (
//    navController: NavHostController
//){
//    NavHost(navController = navController, startDestination = "mainScreen"){
//        composable("mainScreen"){
//            MainScreen()
//        }
//        composable("stockScreen"){
//            StockScreen(stockList = stockList)
//        }
//        composable("stockInputScreen"){
//            StockInputScreen()
//        }
//    }
//}