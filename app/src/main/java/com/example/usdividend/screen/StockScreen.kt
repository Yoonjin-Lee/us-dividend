package com.example.usdividend.screen

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usdividend.*
import com.example.usdividend.R
import com.example.usdividend.activity.StockInputActivity
import com.example.usdividend.data.type.StockListCard
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun StockScreen(
    context: Context,
    sharedViewModel: SharedViewModel = viewModel()
) {
    var stockList = remember {
        mutableStateListOf<StockListCard>()
    }

    var getStockList by remember {
        mutableStateOf(true)
    }

    if (getStockList) {
        //로그인 시 받은 보유 달러 저장
//        sharedViewModel.dollars = holdingDollars!!.toFloat()

//        authService.getStockList(userid!!).enqueue(object : Callback<String> {
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
//                if (response.isSuccessful) {
//                    val data = JSONObject(response.body().toString()).getJSONArray("result")
//
//                    if (data != null) {
//                        //데이터가 잘 왔는지 로그 찍어보기
//                        Log.d("retrofit", "유저 id로 보유주식 얻기")
//                        Log.d("test_retrofit", "받은 정보 :" + data)
//
//                        val list = ArrayList<JSONObject>()
//
//                        for (i in 0 until data.length()) {
//                            list.add(
//                                data.getJSONObject(i)
//                            )
//                        }
//
//                        for (i in list) {
//                            //주식 리스트에 등록
//                            stockList.add(
//                                StockListCard(
//                                    company = i.getString("stockName"),
//                                    quantity = i.getString("quantity"),
//                                    exchange = i.getString("exchangeRate"),
//                                    price = i.getString("price")
//                                )
//                            )
//                            //회사 리스트에 등록
//                            companyList.add(i.getString("stockName"))
//                            Log.d("retrofit", "companyList : ${companyList}")
//                            //주식 아이디 리스트에 등록
//                            stockIdList.add(
//                                StockIdData(
//                                    holdingId = i.getString("holdingId").toInt(),
//                                    stockName = i.getString("stockName"),
//                                    stockId = i.getString("stockId").toInt(),
//                                    quantity = i.getString("quantity").toInt()
//                                )
//                            )
//                            Log.d("retrofit", "stockIdList : ${stockIdList}")
//                        }
//                    } else {
//                        //정보를 받지 못했을 때 로그 찍기
//                        Log.d("retrofit", "유저 id로 보유주식 얻기")
//                        Log.w("retrofit", "실패 ${response.code()}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.d("retrofit", "유저 id로 보유주식 얻기")
//                Log.w("retrofit", "정보 접근 실패", t)
//            }
//        })
        getStockList = false
    }

    val intent = Intent(context, StockInputActivity::class.java)

    val register = object : OnStockRegister {
        override fun register(stockListCard: StockListCard) {
            stockList.add(stockListCard)
            Log.d("stockList", "완료")
            Log.d("stockList", "${stockList.size}")
        }
    }
    setStockRegister(register)

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
            var exchangeData by remember {
                mutableStateOf("0000")
            }
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
                    authService.getExchange().enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                val data = JSONObject(
                                    response.body().toString()
                                ).getString("result")

                                if (data != null) {
                                    //데이터가 잘 왔는지 로그 찍어보기
                                    Log.d("test_retrofit", "받은 정보 :" + data)
                                    exchangeData = data
                                } else {
                                    //정보를 받지 못했을 때 로그 찍기
                                    Log.w("retrofit", "환율 실패 ${response.code()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.w("retrofit", "환율 접근 실패", t)
                            Log.w("retrofit", "환율 접근 실패 response")
                        }
                    })
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
                            startActivity(context, intent, null)
                        }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_round_plus),
                            contentDescription = stringResource(id = R.string.add)
                        )
                    }
                }
                LazyColumn( // scroll view main part
                ) {
                    items(stockList) {
                        StockListDump(stockListCard = it)
                    }
                }
            }
        }
    }
}

@Composable
fun StockListDump(stockListCard: StockListCard) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(90.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Gray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = stockListCard.company.substring(0, 1),
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .drawBehind {
                        drawCircle(
                            color = Main,
                            radius = this.size.maxDimension / 2.0f
                        )
                    },
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = stockListCard.company,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f, true))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.quantity) + "${stockListCard.quantity}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = stringResource(id = R.string.exchange) + "${stockListCard.exchange}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = stringResource(id = R.string.price) + "${stockListCard.price}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewStockScreen() {
//    StockScreen(stockList = stockList)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewStockList() {
    StockListDump(StockListCard("Apple", "3", "1300", "130"))
}