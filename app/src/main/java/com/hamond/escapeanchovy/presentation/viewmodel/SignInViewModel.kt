package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.ApiKeys.GOOGLE_CLIENT_ID
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.domain.usecase.GoogleSignInUseCase
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.utils.AccountUtils.saveUid
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String>?> get() = _loginResult

    fun signInWithGoogle(){
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
            _loginResult.value = googleSignInUseCase(result)
        }
    }

    fun initLoginResult() {
        _loginResult.value = null // 로그인 결과를 null로 초기화
    }
}
