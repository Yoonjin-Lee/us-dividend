package com.example.usdividend.view.input

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.data.type.CompanyData
import com.example.usdividend.data.type.StockData
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main

@Composable
fun StockInputView(
    viewModel: StockInputViewModel = hiltViewModel()
) {
    var name by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }
    var dividend by remember {
        mutableStateOf("")
    }
    var exchange by remember {
        mutableStateOf("")
    }

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
        bottomBar = { // 확인 버튼
            TextButton(
                onClick = {
                    if (
                        name.isNotEmpty() &&
                        quantity.isNotEmpty() &&
                        exchange.isNotEmpty() &&
                        price.isNotEmpty() &&
                        dividend.isNotEmpty()
                    ) {
                        /*********정보 저장*********/
                        viewModel.insert(
                            StockData(
                                0,
                                name,
                                quantity,
                                exchange,
                                price,
                                dividend
                            )
                        )
                        viewModel.insertCompany(
                            CompanyData(
                                name
                            )
                        )
                        /*********보유 달러 업데이트*********/
                        viewModel.updateHoldingdollar(
                            price, quantity
                        )
                          viewModel.finish()
                    } else {
                        viewModel.showToast("모든 정보를 입력해 주세요.")
                    }

                },
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
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        //주식명
                        Text(
                            text = stringResource(id = R.string.stock_name),
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicTextField(
                            value = name,
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            ),
                            onValueChange = { name = it },
                            maxLines = 1,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.dp, Gray,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(47.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (name.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.stock_name_info),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        //매입가
                        Text(
                            text = stringResource(id = R.string.stock_price),
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicTextField(
                            value = price,
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            ),
                            onValueChange = { price = it },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.dp, Gray,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(47.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (price.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.stock_price_info),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        //수량
                        Text(
                            text = stringResource(id = R.string.stock_quantity),
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicTextField(
                            value = quantity,
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            ),
                            onValueChange = { quantity = it },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.dp, Gray,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(47.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (quantity.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.stock_price_info),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        //배당금
                        Text(
                            text = stringResource(id = R.string.stock_dividend),
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicTextField(
                            value = dividend,
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            ),
                            onValueChange = { dividend = it },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.dp, Gray,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(47.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (dividend.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.stock_price_info),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier.padding(0.dp, 20.5.dp, 0.dp, 6.5.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        //구매 환율
                        Text(
                            text = stringResource(id = R.string.stock_exchange),
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        BasicTextField(
                            value = exchange,
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            ),
                            onValueChange = { exchange = it },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.dp, Gray,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .fillMaxWidth()
                                        .height(47.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (exchange.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.stock_price_info),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

fun Context.findActivity(): ComponentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    Log.d("activity", "no")
    throw IllegalStateException("no activity")
}
