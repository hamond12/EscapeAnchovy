package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.constants.Secret.GOOGLE_CLIENT_ID
import com.hamond.escapeanchovy.domain.usecase.GoogleLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val googleLoginUseCase: GoogleLoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

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
            .setServerClientId(GOOGLE_CLIENT_ID)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun handleGoogleLoginState(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    googleLoginUseCase.invoke(firebaseCredential) { isSuccess, result ->
                        if (isSuccess) {
                            _loginResult.value = Result.success(result)
                        } else {
                            _loginResult.value = Result.failure(Exception(result))
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
