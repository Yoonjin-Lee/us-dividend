package com.example.usdividend

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.usdividend.data.NaviDestination
import com.example.usdividend.screen.StockScreen
import com.example.usdividend.screen.stockList
import com.example.usdividend.ui.theme.UsDividendTheme

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf<NaviDestination>(
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.money_icon),
            "배당",
            "dividned",
//            { DividendScreen() }
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.stock_icon),
            "주식",
            "stock",
//            { StockScreen(stockList = stockList)}
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.setting_icon),
            name = "설정",
            "setting",
//            { SettingScreen() }
        )
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.stock),
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
            //content depended on NavigationBar's selectedItem
            when (selectedItem) {
                0 -> Box(modifier = Modifier.padding(it)) {
                    DividendScreen()
                }
                1 -> Box(modifier = Modifier.padding(it)) {
                    //Box에만 modifier를 주고 띄울 스크린에는 modifier를 주면 안 되는 구나..
                    StockScreen(stockList = stockList)
                }
                2 -> Box(modifier = Modifier.padding(it)) {
                    SettingScreen()
                }
            }
        }
    )
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMainScreen() {
    UsDividendTheme {
        MainScreen()
    }
}