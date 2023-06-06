package com.example.usdividend.activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.core.content.ContextCompat.startActivity
import com.example.usdividend.R
import com.example.usdividend.authService
import com.example.usdividend.data.NameEmailData
import com.example.usdividend.data.UserData
import com.example.usdividend.server.ApiService
import com.example.usdividend.server.getRetrofit
import com.example.usdividend.ui.theme.UsDividendTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsDividendTheme {
                // SDK init
                KakaoSdk.init(this, "26e5b8e9d843a4f91b1462baf6b32a7f")
                NaverIdLoginSDK.initialize(this, "tIGpq641j4SBdTs51i0a", "7Y9S0bcjPs", "미주정복")

                LoginPage(context = this@LoginActivity)
            }
        }
    }
}


@Composable
fun LoginPage(
    context: Context
) {
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
//                kakaoLogin(context)
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
            onClick = { naverLogin(context) },
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

var email: String? = null
var nickname: String? = null
var userid: Int? = null
var holdingDollars : Float? = null

fun kakaoLogin(
    context: Context
) {
    // 로그인 조합 예제
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e(TAG, "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Log.i(
                        TAG, "사용자 정보 요청 성공" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                    )

                    email = user.kakaoAccount?.email
                    nickname = user.kakaoAccount?.profile?.nickname

                    /********************서버 연결***********************/
                    authService.signUp(NameEmailData(userName = nickname!!, email = email!!)).enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: retrofit2.Response<String>
                        ) {
                            if (response.isSuccessful) {
                                val data = JSONObject(JSONObject(response.body().toString()).getString("result"))
                                val userdata = UserData(
                                    userId = data.getString("userId").toInt(),
                                    userName = data.getString("userName"),
                                    email = data.getString("email"),
                                    holdingDollar = data.getString("holdingDollar").toFloat()
                                )

                                if (data != null) {
                                    //데이터가 잘 왔는지 로그 찍어보기
                                    Log.d("test_retrofit", "받은 정보 :" + data)

                                    userid = userdata.userId
                                    holdingDollars = userdata.holdingDollar

                                    /**페이지 넘기기**/
                                    val intent = Intent(context, MainActivity::class.java)
                                    startActivity(context, intent, null)
                                } else {
                                    //정보를 받지 못했을 때 로그 찍기
                                    Log.w("retrofit", "실패 ${response.code()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.w("retrofit", "정보 접근 실패", t)
                            Log.w("retrofit", "정보 접근 실패 response")
                        }
                    })
                }
            }
        }
    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            TAG, "사용자 정보 요청 성공" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                        )

                        // 이메일, 닉네임 가져오기
                        email = user.kakaoAccount?.email
                        nickname = user.kakaoAccount?.profile?.nickname

                        /********************서버 연결***********************/
                        authService.signUp(NameEmailData(userName = nickname!!, email = email!!)).enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: retrofit2.Response<String>
                            ) {
                                if (response.isSuccessful) {
                                    val data = JSONObject(JSONObject(response.body().toString()).getString("result"))
                                    val userdata = UserData(
                                        userId = data.getString("userId").toInt(),
                                        userName = data.getString("userName"),
                                        email = data.getString("email"),
                                        holdingDollar = data.getString("holdingDollar").toFloat()
                                    )

                                    if (data != null) {
                                        //데이터가 잘 왔는지 로그 찍어보기
                                        Log.d("test_retrofit", "받은 정보 :" + data)

                                        userid = userdata.userId
                                        holdingDollars = userdata.holdingDollar

                                        /**페이지 넘기기**/
                                        val intent = Intent(context, MainActivity::class.java)
                                        startActivity(context, intent, null)
                                    } else {
                                        //정보를 받지 못했을 때 로그 찍기
                                        Log.w("retrofit", "실패 ${response.code()}")
                                    }
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.w("retrofit", "정보 접근 실패", t)
                                Log.w("retrofit", "정보 접근 실패 response")
                            }
                        })                    }
                }
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

fun naverLogin(
    context: Context
) {
    var naverToken: String? = ""

    val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(response: NidProfileResponse) {
            val userEmail = response.profile?.email.toString()
            val userNickname = response.profile?.nickname
            Log.d("login", "email: ${userEmail}, nickName: ${userNickname}, \ntoken: ${naverToken}")
            if (userNickname == null) {
                nickname = userEmail
            } else {
                nickname = userNickname
            }
            email = userEmail

            /********************서버 연결***********************/
            authService.signUp(NameEmailData(userName = nickname!!, email = email!!)).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: retrofit2.Response<String>
                ) {
                    if (response.isSuccessful) {
                        val data = JSONObject(JSONObject(response.body().toString()).getString("result"))
                        val userdata = UserData(
                            userId = data.getString("userId").toInt(),
                            userName = data.getString("userName"),
                            email = data.getString("email"),
                            holdingDollar = data.getString("holdingDollar").toFloat()
                        )

                        if (data != null) {
                            //데이터가 잘 왔는지 로그 찍어보기
                            Log.d("test_retrofit", "받은 정보 :" + data)

                            userid = userdata.userId
                            holdingDollars = userdata.holdingDollar

                            /**페이지 넘기기**/
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(context, intent, null)
                        } else {
                            //정보를 받지 못했을 때 로그 찍기
                            Log.w("retrofit", "실패 ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.w("retrofit", "정보 접근 실패", t)
                    Log.w("retrofit", "정보 접근 실패 response")
                }
            })
        }

        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            Log.d("naverLogin", "errorCode : ${errorCode}, errorDescription : ${errorDescription}")
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
            naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

            //로그인 유저 정보 가져오기
            NidOAuthLogin().callProfileApi(profileCallback)
        }

        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            Log.d("naverLogin", "errorCode : ${errorCode}, errorDescription : ${errorDescription}")
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DefaultPreview() {
    UsDividendTheme {

    }
}