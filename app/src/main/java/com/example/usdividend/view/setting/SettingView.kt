package com.example.usdividend.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.view.LogoutDialog
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.Red

@Composable
fun SettingView(
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.White)
        )
        SettingTitle(
            settingViewModel
        )
        SettingEmail(settingViewModel)
    }
}

@Composable
fun SettingTitle(
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    var nickname : String? = null
    settingViewModel.nickname.observe(LocalLifecycleOwner.current){
        nickname = it
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Main),
        contentAlignment = Alignment.CenterStart
    ) {
        //닉네임
        Text(
            text = nickname!!,
            modifier = Modifier.padding(12.dp, 20.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@Composable
fun SettingEmail(
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    var email : String? = null
    settingViewModel.email.observe(LocalLifecycleOwner.current){
        email = it
    }

    Column {
        Text(
            text = stringResource(id = R.string.account),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(12.dp, 8.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray)
        )
        // 이메일
        Text(
            text = email!!,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(12.dp, 15.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray)
        )

        var logoutIsClicked by remember {
            mutableStateOf(false)
        }

        if (logoutIsClicked) {
            LogoutDialog(settingViewModel)
        }

        // 로그아웃 버튼
        TextButton(onClick = {
            logoutIsClicked = true
        }) {
            Text(
                text = stringResource(id = R.string.logout),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Red
                )
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray)
        )
    }
}