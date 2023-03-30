package com.example.usdividend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usdividend.R
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.UsDividendTheme

@Composable
fun StockInputScreen(
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.stock_input),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Main)
            )
        },
        bottomBar = {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Main, RectangleShape)
            ) {
                Text(
                    text = stringResource(id = R.string.check),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(0.dp, 13.5.dp)
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            StockInputContent()
        }
    }
}

@Composable
fun StockInputContent() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.stock_name),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextField(
                    value = stringResource(id = R.string.stock_name_info),
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = TextFieldDefaults.outlinedShape,
                    enabled = true,
                    textStyle = TextStyle(
                        color = Gray,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.border(1.dp, Gray, RectangleShape)
                )
            }
        }
        Box(
            modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.stock_price),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextField(
                    value = stringResource(id = R.string.stock_price_info),
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = TextFieldDefaults.outlinedShape,
                    enabled = true,
                    textStyle = TextStyle(
                        color = Gray,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.border(1.dp, Gray, RectangleShape)
                )
            }
        }
        Box(
            modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.stock_quantity),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextField(
                    value = stringResource(id = R.string.stock_price_info),
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = TextFieldDefaults.outlinedShape,
                    enabled = true,
                    textStyle = TextStyle(
                        color = Gray,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.border(1.dp, Gray, RectangleShape)
                )
            }
        }
        Box(
            modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.stock_dividend),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextField(
                    value = stringResource(id = R.string.stock_price_info),
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = TextFieldDefaults.outlinedShape,
                    enabled = true,
                    textStyle = TextStyle(
                        color = Gray,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.border(1.dp, Gray, RectangleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewStockInputScreen() {
    UsDividendTheme {
        StockInputScreen()
    }
}