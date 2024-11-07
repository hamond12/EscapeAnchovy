package com.hamond.escapeanchovy.data.repository.naverLogin

import android.content.Context
import com.hamond.escapeanchovy.data.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NaverLoginRepositoryImpl @Inject constructor() : NaverLoginRepository {

    override suspend fun loginWithNaverAccount(context: Context): String? {
        return suspendCoroutine { continuation ->
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    val accessToken = NaverIdLoginSDK.getAccessToken()
                    continuation.resume(accessToken)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    continuation.resumeWithException(
                        Exception("에러 코드:$errorCode, 에러 내용:$errorDescription")
                    )
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
        }
    }

    override suspend fun getNaverUser(accessToken: String?): User {
        return suspendCancellableCoroutine { continuation ->
            val userInfoUrl = "https://openapi.naver.com/v1/nid/me"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(userInfoUrl)
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    responseBody?.let {
                        val jsonObject = JSONObject(responseBody)
                        if (jsonObject.getString("resultcode") == "00") {
                            val user = jsonObject.getJSONObject("response")
                            val email = user.getString("email")
                            val name = user.getString("name")
                            continuation.resume(User(email, name, ""))
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(Exception("네이버 유저 정보 가져오기 실패: ${e.message}"))
                }
            })

            continuation.invokeOnCancellation {
                client.dispatcher.cancelAll()
            }
        }
    }
}