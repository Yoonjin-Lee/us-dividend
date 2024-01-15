package com.example.usdividend.view.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usdividend.R
import com.example.usdividend.ui.theme.UsDividendTheme

@Composable
fun LoginView(viewModel: LoginViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.project_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, 70.dp, 0.dp, 70.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp, 250.dp)
        )
        IconButton(
            onClick = {
                viewModel.naverLogin()
            },
            modifier = Modifier
                .padding(0.dp, 70.dp, 0.dp, 10.dp)
                .size(320.dp, 45.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.btn_login_kakao),
                contentDescription = null,
                modifier = Modifier.size(320.dp, 45.dp),
                tint = Color.Unspecified
            )
        }
        IconButton(
            onClick = {
                viewModel.kakaoLogin()
            },
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                .size(320.dp, 45.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_login_naver),
                contentDescription = null,
                modifier = Modifier.size(320.dp, 45.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DefaultPreview() {
    UsDividendTheme {

    }
}