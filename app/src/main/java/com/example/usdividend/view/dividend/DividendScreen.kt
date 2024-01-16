package com.example.usdividend

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usdividend.activity.DividendHistoryActivity
import com.example.usdividend.screen.DividendDialog
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.UsDividendTheme
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

@Composable
fun DividendScreen(
    context: Context,
    sharedViewModel: SharedViewModel = viewModel()
) {

    // dialog 에서 변경된 값을 가지고 리스트 요소 삭제
    if (sharedViewModel.myVariable) {
        companyList.remove(sharedViewModel.myCompany)
        sharedViewModel.myVariable = false
    }

    var getDividendHistory by remember {
        mutableStateOf(true)
    }

    val cList = remember {
        mutableStateListOf<String>()
    }

    /************배당 목록 가져오기************/
    if (getDividendHistory) {
        for (i in companyList){
            cList.add(i)
        }
//        authService.getDividendHistory(userId = userid!!).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    val data = JSONObject(response.body().toString()).getJSONArray("result")
//
//                    if (data != null) {
//                        //데이터가 잘 왔는지 로그 찍어보기
//                        Log.d("retrofit", "배당 목록 가져오기")
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
//                            //배당 리스트에 등록
//                            dividendList.add(
//                                DividendListData(
//                                    stockName = i.getString("stockName"),
//                                    dividend = i.getString("dividend").toFloat(),
//                                    createdMonth = i.getString("createdAt").toString()
//                                        .split("-")[1].toInt()
//                                )
//                            )
//                            Log.d("retrofit", "dividendList : ${dividendList}")
//                        }
//                    } else {
//                        //정보를 받지 못했을 때 로그 찍기
//                        Log.d("retrofit", "배당 목록 가져오기")
//                        Log.w("retrofit", "실패 ${response.code()}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.d("retrofit", "배당 목록 가져오기")
//                Log.w("retrofit", "실패", t)
//            }
//        })
        getDividendHistory = false
    }

    // BarChart data
    val entries: ArrayList<BarEntry> = ArrayList()
    val monthDividend: Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    for (i: Int in 0 until dividendList.size) {
        monthDividend[dividendList[i].createdMonth - 1] += dividendList[i].dividend
    }
    entries.apply {
        entries.add(BarEntry(1f, monthDividend[0]))
        entries.add(BarEntry(2f, monthDividend[1]))
        entries.add(BarEntry(3f, monthDividend[2]))
        entries.add(BarEntry(4f, monthDividend[3]))
        entries.add(BarEntry(5f, monthDividend[4]))
        entries.add(BarEntry(6f, monthDividend[5]))
        entries.add(BarEntry(7f, monthDividend[6]))
        entries.add(BarEntry(8f, monthDividend[7]))
        entries.add(BarEntry(9f, monthDividend[8]))
        entries.add(BarEntry(10f, monthDividend[9]))
        entries.add(BarEntry(11f, monthDividend[10]))
        entries.add(BarEntry(12f, monthDividend[11]))
    }

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        DollarPart()
        MonthlyList(cList)
        DividedTitle(text = stringResource(id = R.string.table_title))
        BarChartView(data = entries)
        Row {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    val intent = Intent(context, DividendHistoryActivity::class.java)
                    startActivity(context, intent, null)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.dividend_button),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .background(Main, RoundedCornerShape(12.dp))
                        .size(74.dp, 29.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun DollarPart(
    sharedViewModel: SharedViewModel = viewModel()
) {
    Box(
        Modifier
            .background(Gray, RectangleShape)
            .fillMaxWidth()
            .padding(17.dp, 22.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.my_dollars),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
//                text = sharedViewModel.dollars.toString(),
                text = "",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun MonthlyList(
    companyList: SnapshotStateList<String>
) {
    Modifier
        .fillMaxWidth()
        .height(112.dp)
    Column {
        //Title part
        Row(
            Modifier.padding(14.dp)
        ) {
            Text(
                text = "3",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = stringResource(id = R.string.monthly_dividend),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Box(
            Modifier
                .padding(18.dp, 0.dp)
                .height(80.dp)
        ) {
                CompanyList(companyList = companyList)
        }
    }
}

@Composable
fun CompanyList(
    companyList: SnapshotStateList<String>
) {
    LazyColumn() {
        items(companyList) {
            CompanyListCard(it)
        }
    }
}

@Composable
fun CompanyListCard(
    company: String
) {
    // show dialog
    var isClicked by remember {
        mutableStateOf(false)
    }
    if (isClicked) {
        DividendDialog(v = true, company)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            isClicked = true
        }
    ) {
        Text(text = stringResource(id = R.string.dot))
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = company,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun DividedTitle(
    text: String
) {
    Box(
        Modifier
            .fillMaxWidth()
            .border(1.dp, Gray, RectangleShape)
            .padding(14.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        )
    }
}

@Composable
fun BarChartView(data: ArrayList<BarEntry>) {
//    val entries = data.mapIndexed { index, pair -> BarEntry(index.toFloat(), pair.second) }
    val dataSet = BarDataSet(data, "Values")
    dataSet.setColors(R.color.main_color)
    val barData = BarData(dataSet)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(235.dp),
            factory = { context ->
                BarChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setData(barData)
                    setNoDataText("No data available")
                    description.isEnabled = false
                    legend.isEnabled = false
                    setScaleEnabled(false)
                    setTouchEnabled(false)
                    axisLeft.axisMinimum = 1f
                    axisRight.axisMinimum = 12f
                    axisLeft.isEnabled = false
                    axisRight.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.granularity = 1f
                    xAxis.labelCount = 12
                    xAxis.setDrawGridLines(false)
                }
            },
            update = { chart ->
                chart.data = barData
                chart.invalidate()
            }
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDividendScreen() {
    UsDividendTheme {
//        DividendScreen()
    }
}