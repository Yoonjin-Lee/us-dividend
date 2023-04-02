package com.example.usdividend.screen

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.*
import androidx.core.content.ContextCompat.startActivity
import com.example.usdividend.MainActivity
import com.example.usdividend.R
import com.example.usdividend.StockInputActivity
import com.example.usdividend.data.StockListCard
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.UsDividendTheme

@Composable
fun StockInputScreen(
    context: Context
) {
    val cur = LocalContext.current
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
                    mystockRegister!!.register(
                        StockListCard(
                            company = Name,
                            quantity = Quantitiy,
                            price = Price,
                            dividend = Dividend,
                            exchange = Exchange
                        )
                    )
                    Log.d("value", "${Name} ${Quantitiy}")
                    Log.d("register", "완료")
                    cur.findActivity().finish()
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
            StockInputContent()
        }
    }
}

var Name : String = ""
var Price : String = ""
var Quantitiy : String = ""
var Dividend : String = ""
var Exchange : String = ""

@Composable
fun StockInputContent(
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

    Name = name
    Price = price
    Quantitiy = quantity
    Dividend = dividend
    Exchange = exchange

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

interface OnStockRegister{
    fun register(stockListCard: StockListCard)
}

var mystockRegister : OnStockRegister? = null
fun setStockRegister(istockRegister: OnStockRegister) {
    mystockRegister = istockRegister
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewStockInputScreen() {
    UsDividendTheme {
//        StockInputScreen()
    }
}