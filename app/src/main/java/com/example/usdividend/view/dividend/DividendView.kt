package com.example.usdividend.view.dividend

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.data.type.DividendData
import com.example.usdividend.view.DividendDialog
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped

@Composable
@ViewScoped
fun DividendView(
    viewModel: DividendViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        DollarPart(viewModel)
        MonthlyList()
        DividedTitle(text = stringResource(id = R.string.table_title))
        BarChartView()
        Row {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    viewModel.move()
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
    viewModel: DividendViewModel = hiltViewModel()
) {
    val dollar by viewModel.dollar.observeAsState()
    Box(
        Modifier
            .background(Gray, RectangleShape)
            .fillMaxWidth()
            .padding(17.dp, 10.dp)
            .defaultMinSize(minHeight = 40.dp)
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
            Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
            Text(
                text = dollar!!,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            IconButton(onClick = {
                viewModel.getDollar()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.reset_icon),
                    contentDescription = stringResource(id = R.string.reset)
                )
            }
        }
    }
}

@Composable
fun MonthlyList(
    viewModel: DividendViewModel = hiltViewModel()
) {
    var month = ""
    viewModel.month.observe(LocalLifecycleOwner.current) {
        month = it
    }
    Modifier
        .fillMaxWidth()
        .height(100.dp)
    Column {
        //Title part
        Row(
            Modifier.padding(14.dp)
        ) {
            Text(
                text = month,
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
            CompanyList()
        }
    }
}

@Composable
fun CompanyList(
    viewModel : DividendViewModel = hiltViewModel()
) {
    /************주식 이름 목록 가져오기*************/
    val stockNameList by viewModel.stockNameList.observeAsState(initial = emptyList())

    LazyColumn() {
        items(stockNameList) {
            CompanyListCard(it, stockNameList.toMutableStateList())
        }
    }
}

@Composable
fun CompanyListCard(
    company: String,
    companyList: SnapshotStateList<String>
) {
    // show dialog
    var isClicked by remember {
        mutableStateOf(false)
    }
    if (isClicked) {
        DividendDialog(
            true,
            company,
            onNegativeClick = {
                isClicked = !isClicked
            },
            onPositiveClick = {
                companyList.remove(company)
                isClicked = !isClicked
            }
        )
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
fun BarChartView(
    viewModel: DividendViewModel = hiltViewModel()
) {
//    val entries = data.mapIndexed { index, pair -> BarEntry(index.toFloat(), pair.second) }
    /************배당 목록 가져오기*************/
    val dividendList by viewModel.dividendList.observeAsState(initial = emptyList())

    // BarChart data
    val entries: ArrayList<BarEntry> = ArrayList()
    val monthDividend: Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    for (i: Int in 0 until dividendList.size) {
        monthDividend[dividendList[i].month.toInt() - 1] += dividendList[i].stockDividend.toFloat()
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

    val dataSet = BarDataSet(entries, "Values")
    dataSet.setColors(R.color.main_color)
    val barData = BarData(dataSet)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
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