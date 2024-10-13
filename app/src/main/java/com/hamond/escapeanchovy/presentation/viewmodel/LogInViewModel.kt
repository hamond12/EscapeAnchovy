package com.hamond.escapeanchovy.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.AndroidViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.BuildConfig
import com.hamond.escapeanchovy.domain.usecase.GoogleLoginUseCase
import com.hamond.escapeanchovy.domain.usecase.SaveSocialAccountInfoUseCase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    application: Application,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val saveSocialAccountInfoUseCase: SaveSocialAccountInfoUseCase
) : AndroidViewModel(application) {

    companion object{
        const val TAG = "kakaoLogin"
    }

    private val context = application.applicationContext

    private val _loginResult = MutableStateFlow<Result<String?>?>(null)
    val loginResult: StateFlow<Result<String?>?> get() = _loginResult

    suspend fun googleLogin(
        onFailure: () -> Unit
    ) {
        val credentialManager = CredentialManager.create(context)
        val request = createCredentialRequest()

        runCatching {
            credentialManager.getCredential(request = request, context = context)
        }.onSuccess { credentialResponse ->
            handleGoogleLoginState(credentialResponse)
        }.onFailure {
            onFailure()
        }
    }

    private fun createCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // 모든 Google 계정 포함
            .setAutoSelectEnabled(true) // Google 계정을 자동으로 선택
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun handleGoogleLoginState(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    googleLoginUseCase.invoke(firebaseCredential) { error, user ->
                        if (error == null) {
                            saveSocialAccountInfoUseCase.invoke(user) { error ->
                                if (error == null) {
                                    _loginResult.value = Result.success(user?.email)
                                } else {
                                    _loginResult.value = Result.failure(Exception(error))
                                }
                            }
                        } else {
                            _loginResult.value = Result.failure(Exception(error))
                        }
                    }
                } else {
                    _loginResult.value = Result.failure(Exception("Unsupported credential type"))
                }
            }
            else -> _loginResult.value = Result.failure(Exception("Invalid credential type"))
        }
    }

    fun initLoginResult() {
        _loginResult.value = null
    }
}
