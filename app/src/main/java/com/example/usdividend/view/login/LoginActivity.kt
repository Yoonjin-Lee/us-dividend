package com.example.usdividend.view.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.usdividend.ui.theme.UsDividendTheme
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                // SDK init
                KakaoSdk.init(this, "26e5b8e9d843a4f91b1462baf6b32a7f")
                NaverIdLoginSDK.initialize(this, "tIGpq641j4SBdTs51i0a", "7Y9S0bcjPs", "미주정복")

                LoginView(viewModel)
            }
        }
    }
}
