package com.example.usdividend.view

import android.content.Context
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
import com.example.usdividend.ui.theme.Red
import com.example.usdividend.view.setting.SettingViewModel

@Composable
fun LogoutDialog(
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    var visible by remember {
        mutableStateOf(true)
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
                        tint = Red
                    )
                    Text(
                        text = stringResource(id = R.string.logout_info),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium,
                            color = Red
                        ),
                        modifier = Modifier.padding(0.dp,0.dp,0.dp,15.dp)
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
                                settingViewModel.logout()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .background(Red)
                        ) {
                            Text(
                                text = stringResource(id = R.string.logout),
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