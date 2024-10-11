package com.hamond.escapeanchovy.presentation.viewmodel

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import android.provider.Settings.ACTION_ADD_ACCOUNT
import android.provider.Settings.ACTION_SYNC_SETTINGS
import android.provider.Settings.EXTRA_ACCOUNT_TYPES
import android.provider.Settings.EXTRA_AUTHORITIES
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.constants.ApiKeys.GOOGLE_CLIENT_ID
import com.hamond.escapeanchovy.domain.usecase.GoogleLoginUseCase
import com.hamond.escapeanchovy.utils.CommonUtils.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
            handleCredential(credentialResponse)
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

    private fun handleCredential(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    handleGoogleLoginState(firebaseCredential)
                } else {
                    _loginResult.value = Result.failure(Exception("Unsupported credential type"))
                }
            }
            else -> _loginResult.value = Result.failure(Exception("Invalid credential type"))
        }
    }

    private fun handleGoogleLoginState(firebaseCredential: AuthCredential) {
        googleLoginUseCase.invoke(firebaseCredential) { isSuccess, result ->
            if (isSuccess) {
                _loginResult.value = Result.success(result)
            } else {
                _loginResult.value = Result.failure(Exception(result))
            }
        }
    }

    fun initLoginResult() {
        _loginResult.value = null
    }
}
