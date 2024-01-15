package com.example.usdividend.view.login

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usdividend.activity.MainActivity
import com.example.usdividend.data.repository.LoginRepository
import com.example.usdividend.data.type.NameEmailData
import com.example.usdividend.data.type.UserData
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
@AndroidEntryPoint
class LoginViewModel @Inject constructor(
    @ActivityContext private val context: Context
) : ViewModel() {
    @Inject
    lateinit var loginRepository: LoginRepository

    var email: String? = null
    var nickname: String? = null
    var userid: Int? = null
    var holdingDollars: Float? = null

    fun kakaoLogin(
    ) {
        // 로그인 조합 예제
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            ContentValues.TAG, "사용자 정보 요청 성공" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                        )

                        email = user.kakaoAccount?.email
                        nickname = user.kakaoAccount?.profile?.nickname

                        /********************서버 연결***********************/
                        viewModelScope.launch {
                            val serverAnswer = loginRepository.signUp(
                                NameEmailData(nickname!!, email!!)
                            )
                            when (serverAnswer.isSuccessful) {
                                true -> {
                                    val data = JSONObject(
                                        JSONObject(
                                            serverAnswer.body().toString()
                                        ).getString("result")
                                    )
                                    val userdata = UserData(
                                        userId = data.getString("userId").toInt(),
                                        userName = data.getString("userName"),
                                        email = data.getString("email"),
                                        holdingDollar = data.getString("holdingDollar")
                                            .toFloat()
                                    )
                                    userid = userdata.userId
                                    holdingDollars = userdata.holdingDollar
                                    val intent = Intent(
                                        context,
                                        MainActivity::class.java
                                    )
                                    startActivity(context, intent, null)
                                }

                                else -> {
                                    Log.d("Retrofit", "data null")
                                }
                            }
                        }
                    }
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(
                                ContentValues.TAG, "사용자 정보 요청 성공" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                            )

                            // 이메일, 닉네임 가져오기
                            email = user.kakaoAccount?.email
                            nickname = user.kakaoAccount?.profile?.nickname

                            /********************서버 연결***********************/
                            viewModelScope.launch {
                                val serverAnswer = loginRepository.signUp(
                                    NameEmailData(nickname!!, email!!)
                                )
                                when (serverAnswer.isSuccessful) {
                                    true -> {
                                        val data = JSONObject(
                                            JSONObject(
                                                serverAnswer.body().toString()
                                            ).getString("result")
                                        )
                                        val userdata = UserData(
                                            userId = data.getString("userId").toInt(),
                                            userName = data.getString("userName"),
                                            email = data.getString("email"),
                                            holdingDollar = data.getString("holdingDollar")
                                                .toFloat()
                                        )
                                        userid = userdata.userId
                                        holdingDollars = userdata.holdingDollar
                                        val intent = Intent(
                                            context,
                                            MainActivity::class.java
                                        )
                                        startActivity(context, intent, null)
                                    }

                                    else -> {
                                        Log.d("Retrofit", "data null")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun naverLogin(
    ) {
        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userEmail = response.profile?.email.toString()
                val userNickname = response.profile?.nickname
                Log.d(
                    "login",
                    "email: ${userEmail}, nickName: ${userNickname}, \ntoken: ${naverToken}"
                )
                if (userNickname == null) {
                    nickname = userEmail
                } else {
                    nickname = userNickname
                }
                email = userEmail

                /********************서버 연결***********************/
                viewModelScope.launch {
                    val serverAnswer = loginRepository.signUp(
                        NameEmailData(nickname!!, email!!)
                    )
                    when (serverAnswer.isSuccessful) {
                        true -> {
                            val data = JSONObject(
                                JSONObject(
                                    serverAnswer.body().toString()
                                ).getString("result")
                            )
                            val userdata = UserData(
                                userId = data.getString("userId").toInt(),
                                userName = data.getString("userName"),
                                email = data.getString("email"),
                                holdingDollar = data.getString("holdingDollar")
                                    .toFloat()
                            )
                            userid = userdata.userId
                            holdingDollars = userdata.holdingDollar
                            val intent = Intent(
                                context,
                                MainActivity::class.java
                            )
                            startActivity(context, intent, null)
                        }

                        else -> {
                            Log.d("Retrofit", "data null")
                        }
                    }
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d(
                    "naverLogin",
                    "errorCode : ${errorCode}, errorDescription : ${errorDescription}"
                )
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
                Log.d(
                    "naverLogin",
                    "errorCode : ${errorCode}, errorDescription : ${errorDescription}"
                )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}