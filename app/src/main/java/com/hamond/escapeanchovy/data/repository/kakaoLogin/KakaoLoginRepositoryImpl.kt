package com.hamond.escapeanchovy.data.repository.kakaoLogin

import android.content.Context
import com.hamond.escapeanchovy.data.model.User
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoLoginRepositoryImpl @Inject constructor():KakaoLoginRepository {

    override suspend fun loginWithKakaoAccount(context: Context) {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context, listOf(Prompt.SELECT_ACCOUNT)) { _, error ->
                if (error != null) {
                    continuation.resumeWithException(Exception("로그인 실패: ${error.message}"))
                } else {
                    continuation.resume(Unit)
                }
            }
        }
    }

    override suspend fun getKakaoUser(): User {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    continuation.resumeWithException(Exception("사용자 정보 요청 실패: ${error.message}"))
                } else {
                    val email = user?.kakaoAccount?.email ?: ""
                    val name = user?.kakaoAccount?.profile?.nickname ?: ""
                    continuation.resume(User(email, name, ""))
                }
            }
        }
    }
}
