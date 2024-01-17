package com.example.usdividend.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usdividend.R
import com.example.usdividend.SharedViewModel
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.UsDividendTheme

@Composable
fun DividendDialog(
    v: Boolean,
    company: String,
    sharedViewModel: SharedViewModel = viewModel()
) {
    var visible by remember {
        mutableStateOf(v)
    }

    if (visible) {
        Dialog(
            onDismissRequest = { visible = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier
                    .width(216.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.mdi_warning_circle_outline
                        ),
                        contentDescription = null,
                        modifier = Modifier.padding(0.dp, 25.dp),
                        tint = Main
                    )
                    Text(
                        text = stringResource(id = R.string.dividend_dialog_title),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium,
                            color = Main
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.dividend_dialog_content),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(0.dp, 15.dp)
                    )
                    Row {
                        TextButton( // no
                            onClick = { visible = false },
                            modifier = Modifier
                                .weight(1f)
                                .background(colorResource(id = R.color.dismiss))
                        ) {
                            Text(
                                text = stringResource(id = R.string.no),
                                style = TextStyle(
                                    color = colorResource(id = R.color.button_gray),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                        TextButton( // yes
                            onClick = {
                                sharedViewModel.myCompany = company
                                sharedViewModel.myVariable = true
                                visible = false

//                                /**********배당금 로그 보내기*********/
//                                authService.postDividend(
//                                    ServerPostDividend(
//                                        holdingId = hId,
//                                        userId = userid!!,
//                                        stockId = sId,
//                                        quantity = q
//                                    )
//                                ).enqueue(object : Callback<String>{
//                                    override fun onResponse(
//                                        call: Call<String>,
//                                        response: Response<String>
//                                    ) {
//                                        if (response.isSuccessful) {
//                                            val data = response.body()
//
//                                            if (data != null) {
//                                                //데이터가 잘 왔는지 로그 찍어보기
//                                                Log.d("retrofit", "배당금 로그 전송")
//                                                Log.d("test_retrofit", "받은 정보 :" + data)
//
//                                            } else {
//                                                //정보를 받지 못했을 때 로그 찍기
//                                                Log.w("retrofit", "배당금 로그 전송 실패 ${response.code()}")
//                                            }
//                                        }
//                                    }
//
//                                    override fun onFailure(call: Call<String>, t: Throwable) {
//                                        Log.w("retrofit", "배당 로그 전송 실패", t)
//                                    }
//                                })
//
//                                /**********보유 달러 변경***********/
//                                authService.getDollars(userid!!).enqueue(object : Callback<String>{
//                                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                                        if (response.isSuccessful){
//                                            val data = JSONObject(response.body().toString()).getString("result")
//
//                                            if (data!=null){
//                                                sharedViewModel.dollars = data.toFloat()
//                                                holdingDollars = data.toFloat()
//                                                Log.d("retrofit", "보유 달러 데이터 : ${data}")
//                                            } else {
//                                                Log.d("retrofit", "보유 달러 데이터 없음")
//                                            }
//                                        }
//                                    }
//
//                                    override fun onFailure(call: Call<String>, t: Throwable) {
//                                        Log.d("retrofit", "보유 달러 업데이트")
//                                        Log.w("retrofit", "정보 접근 실패", t)
//                                    }
//                                })

                            },
                            modifier = Modifier
                                .weight(1f)
                                .background(colorResource(id = R.color.main_color))
                        ) {
                            Text(
                                text = stringResource(id = R.string.yes),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDividendDialog() {
    UsDividendTheme {
        DividendDialog(true, "")
    }
}