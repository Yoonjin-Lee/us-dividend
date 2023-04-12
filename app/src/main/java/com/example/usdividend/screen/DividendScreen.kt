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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
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
    var companyList = remember {
        mutableStateListOf<String>()
    }

    var companyAdd by remember {
        mutableStateOf(true)
    }

    if (companyAdd) {
        companyList.apply {
            add("AT&T")
            add("APPLE")
            add("O")
        }
        companyAdd = false
    }

    // dialog 에서 변경된 값을 가지고 리스트 요소 삭제
    if (sharedViewModel.myVariable) {
        companyList.remove(sharedViewModel.myCompany)
        sharedViewModel.myVariable = false
    }

    // BarChart data
    val entries: ArrayList<BarEntry> = ArrayList()
    entries.apply {
        entries.add(BarEntry(1f, 10f))
        entries.add(BarEntry(2f, 20f))
        entries.add(BarEntry(3f, 30f))
        entries.add(BarEntry(4f, 40f))
        entries.add(BarEntry(5f, 50f))
        entries.add(BarEntry(6f, 50f))
        entries.add(BarEntry(7f, 50f))
        entries.add(BarEntry(8f, 50f))
        entries.add(BarEntry(9f, 50f))
        entries.add(BarEntry(10f, 50f))
        entries.add(BarEntry(11f, 50f))
        entries.add(BarEntry(12f, 50f))
    }

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        DollarPart()
        MonthlyList(companyList)
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
fun DollarPart() {
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
                text = "50",
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