package com.example.usdividend.view.stock

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.data.type.StockListCard
import com.example.usdividend.ui.theme.Gray

@Composable
fun StockView(
    viewModel: StockViewModel = hiltViewModel()
) {
    val stockCardList = remember{
        mutableStateListOf<StockListCard>()
    }

    // viewModel로부터 stockList를 받아 stockListCard 형태로 변환
    viewModel.stockList.observe(LocalLifecycleOwner.current){
        for(stockData in it){
            stockCardList.add(
                StockListCard(
                    stockData.stockName,
                    stockData.stockQuantity,
                    stockData.exchange,
                    stockData.stockPrice
                )
            )
        }
    }

    var getStockList by remember {
        mutableStateOf(true)
    }

    if (getStockList) {
        //로그인 시 받은 보유 달러 저장

        getStockList = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Column( // top part
            modifier = Modifier
                .background(colorResource(id = R.color.gray), RectangleShape)
                .fillMaxWidth()
        ) {
            val exchangeData = "0000"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(18.dp, 23.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dollar_exchange),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp))
                Text(
                    text = exchangeData,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = stringResource(id = R.string.won),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(onClick = {
                    viewModel.showToast("기능 추가 예정")
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.reset_icon),
                        contentDescription = stringResource(id = R.string.reset)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 23.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.my_dollars),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                Text(
                    text = "",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        // Table part
        Box(
            modifier = Modifier
                .padding(11.dp, 11.dp, 11.dp, 0.dp)
                .border(
                    BorderStroke(1.dp, Gray),
                    RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
                )
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.my_stock),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(7.dp, 13.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f, true))
                    IconButton(
                        onClick = {
                            // StockInputActivity로 이동
                            viewModel.move()
                        }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_round_plus),
                            contentDescription = stringResource(id = R.string.add)
                        )
                    }
                }
                LazyColumn( // scroll view main part
                ) {
                    items(stockCardList) {
                        StockListDump(stockListCard = it)
                    }
                }
            }
        }
    }
}