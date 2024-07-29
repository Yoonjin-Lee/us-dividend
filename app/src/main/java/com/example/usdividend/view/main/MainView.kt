package com.example.usdividend.view.main

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.usdividend.R
import com.example.usdividend.data.type.NaviDestination
import com.example.usdividend.navigator.NavigationGraph
import com.example.usdividend.ui.theme.UsDividendTheme
import com.example.usdividend.view.dividend.DividendViewModel
import com.example.usdividend.view.setting.SettingViewModel
import com.example.usdividend.view.stock.StockViewModel

@Composable
fun MainView(
    stockViewModel: StockViewModel = hiltViewModel(),
    dividendViewModel: DividendViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    context: Context
) {
    var selectedItem by remember { mutableIntStateOf(1) }

    val navController = rememberNavController()

    val items = listOf(
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.money_icon),
            stringResource(id = R.string.money),
            "dividendScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.stock_icon),
            stringResource(id = R.string.stock),
            "stockScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.setting_icon),
            stringResource(id = R.string.setting),
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
                            else -> stringResource(id = R.string.stock)
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
                NavigationGraph(
                    navController = navController,
                    context = context,
                    stockViewModel, dividendViewModel, settingViewModel
                )
            }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMainScreen() {
    UsDividendTheme {
//        MainScreen()
    }
}