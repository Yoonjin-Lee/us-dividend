package com.example.usdividend.screen

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usdividend.R
import com.example.usdividend.data.DividendHistoryData
import com.example.usdividend.data.DividendListData
import com.example.usdividend.dividendList
import com.example.usdividend.function.excel
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main


@Composable
fun DividendHistoryScreen(
    context: Context
) {
    val currentContext = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.dividend_history_title),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        currentContext.findActivity().finish()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    DropDownPart(context)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Main)
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                HistoryList(dividendList)
            }
        }
    )
}

@Composable
fun DropDownPart(
    context: Context
) {
    var dropdown by remember {
        mutableStateOf(false)
    }

    var excelMaker by remember {
        mutableStateOf(false)
    }

    if (excelMaker){
        excel(context)
        excelMaker = false
    }

    IconButton(onClick = { dropdown = true }) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.tabler_chart_dots_3),
            tint = Color.White,
            contentDescription = null
        )
    }

    DropdownMenu(
        expanded = dropdown,
        onDismissRequest = { dropdown = false },
        modifier = Modifier.wrapContentSize()
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.excel_file)) },
            onClick = {
                excelMaker = true
            }
        )
    }
}

@Composable
fun HistoryList(
    list: SnapshotStateList<DividendListData>
) {
    LazyColumn() {
        items(list) {
            HistoryCard(it = it)
            Divider(color = Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun HistoryCard(
    it: DividendListData
) {
    Row(
        modifier = Modifier
            .padding(23.dp, 21.dp)
    ) {
        Text(
            text = it.createdMonth.toString(),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        Text(
            text = stringResource(id = R.string.month),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        Text(
            text = it.stockName,
            modifier = Modifier.padding(14.dp, 0.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$${it.dividend}",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
    }
}
