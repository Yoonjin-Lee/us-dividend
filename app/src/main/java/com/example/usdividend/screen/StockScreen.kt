package com.example.usdividend.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.usdividend.R
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main

@Composable
fun StockScreen(
    stockList: java.util.ArrayList<StockListCard>
) {
    Add()
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
                    text = "0000",
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
                IconButton(onClick = { /*TODO*/ }) {
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
                    text = "00",
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
                    IconButton(onClick = { /*TODO*/ }) {
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
                    text = stringResource(id = R.string.dividend) + "${stockListCard.dividend}",
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

data class StockListCard(
    val company: String,
    val quantity: String,
    val dividend: String,
    val exchange: String,
    val price: String
)

val stockList = java.util.ArrayList<StockListCard>()

fun Add() {
    stockList.add(
        StockListCard("Apple", "3", "2.1", "1300", "130")
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewStockScreen() {
    StockScreen(stockList = stockList)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewStockList() {
    StockListDump(StockListCard("Apple", "3", "2.1", "1300", "130"))
}