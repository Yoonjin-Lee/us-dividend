package com.example.usdividend

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usdividend.data.NaviDestination
import com.example.usdividend.screen.StockScreen
import com.example.usdividend.ui.theme.UsDividendTheme
import com.google.android.material.composethemeadapter.*

@Composable
fun MainScreen(
    context: Context
) {
    var selectedItem by remember { mutableStateOf(1) }

    val navController = rememberNavController()

    val items = listOf<NaviDestination>(
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.money_icon),
            "배당",
            "dividendScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.stock_icon),
            "주식",
            "stockScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.setting_icon),
            name = "설정",
            "settingScreen"
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (selectedItem) {
                            0 -> stringResource(id = R.string.money)
                            1 -> stringResource(id = R.string.stock)
                            2 -> stringResource(id = R.string.setting)
                            else -> "주식"
                        },
                        color = colorResource(id = R.color.white)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(colorResource(id = R.color.main_color))
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.main_color),
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(id = R.string.money)
                            )
                        },
                        label = {
                            Text(text = item.name)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.main_color),
                            selectedTextColor = colorResource(id = R.color.main_color),
                            unselectedIconColor = colorResource(id = R.color.gray),
                            unselectedTextColor = colorResource(id = R.color.gray)
                        )
                    )
                }
            }
        },
        content = {
            Box(Modifier.padding(it)) {
                NavigationGraph(navController = navController, context = context)
            }
        }
    )
}

@Composable
fun NavigationGraph(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = "stockScreen") {
        composable("dividendScreen") {
            DividendScreen(context)
        }
        composable("stockScreen") {
            StockScreen(context)
        }
        composable("settingScreen") {
            SettingScreen(context)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMainScreen() {
    UsDividendTheme {
//        MainScreen()
    }
}