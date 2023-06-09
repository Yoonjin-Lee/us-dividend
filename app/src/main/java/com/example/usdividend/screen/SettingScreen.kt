package com.example.usdividend

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usdividend.activity.email
import com.example.usdividend.activity.nickname
import com.example.usdividend.screen.DividendDialog
import com.example.usdividend.screen.LogoutDialog
import com.example.usdividend.ui.theme.Gray
import com.example.usdividend.ui.theme.Main
import com.example.usdividend.ui.theme.Red

@Composable
fun SettingScreen(
    context : Context
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.White)
        )
        SettingTop()
        SettingAccount(context = context)
    }
}

@Composable
fun SettingTop() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Main),
        contentAlignment = Alignment.CenterStart
    ) {
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
fun SettingAccount(
    context: Context
) {
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

        var isClicked by remember {
            mutableStateOf(false)
        }

        if (isClicked) {
            LogoutDialog(v = true, context = context)
        }

        TextButton(onClick = {
            isClicked = true
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