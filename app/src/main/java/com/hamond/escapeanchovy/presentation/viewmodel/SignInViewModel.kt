package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.constants.ApiKeys.GOOGLE_CLIENT_ID
import com.hamond.escapeanchovy.domain.usecase.GoogleLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleLoginUseCase: GoogleLoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String>?> get() = _loginResult

    fun googleLogin(){
        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(GOOGLE_CLIENT_ID).build()

        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        viewModelScope.launch {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            _loginResult.value = googleLoginUseCase(result)
        }
    }

    // 중복된 로그인 처리 방지
    fun initLoginResult() {
        _loginResult.value = null
    }
}
