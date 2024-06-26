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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.view.dividend.DividendViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DividendDialog(
    v: Boolean,
    company: String,
    viewModel: DividendViewModel = hiltViewModel(),
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
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
                            onClick = {
                                onNegativeClick()
                            },
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
                                CoroutineScope(Dispatchers.IO).launch {
                                    /********배당금 데이터베이스 삽입*******/
                                    viewModel.insertDividend(company)
                                    /*******보유 달러 업데이트*******/
                                    viewModel.updateHoldingdollar(company)
                                    viewModel.getDollar()
                                    /*********화면 리스트에서 삭제*******/
                                    onPositiveClick()
                                    /*********데이터 베이스 삭제********/
                                    viewModel.deleteCompany(company)
                                }

                                visible = false
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