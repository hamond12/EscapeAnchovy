package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.provider.Settings.ACTION_ADD_ACCOUNT
import android.provider.Settings.EXTRA_ACCOUNT_TYPES
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import com.hamond.escapeanchovy.data.model.User
import com.hamond.escapeanchovy.data.repository.googleLogin.GoogleLoginRepository
import com.hamond.escapeanchovy.data.repository.kakaoLogin.KakaoLoginRepository
import com.hamond.escapeanchovy.data.repository.naverLogin.NaverLoginRepository
import com.hamond.escapeanchovy.data.repository.store.StoreRepository
import com.hamond.escapeanchovy.presentation.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleLoginRepository: GoogleLoginRepository,
    private val kakaoLoginRepository: KakaoLoginRepository,
    private val naverLoginRepository: NaverLoginRepository,
    private val storeRepository: StoreRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState<String>>(LoginState.Init)
    val loginState = _loginState.asStateFlow()


    suspend fun googleLogin(context: Context) {
        val credentialManager = CredentialManager.create(context)
        val request = googleLoginRepository.createCredentialRequest()

        runCatching {
            credentialManager.getCredential(request = request, context = context)
        }.onSuccess {
            performGoogleLogin(it)
        }.onFailure { error ->
            if (error is GetCredentialCancellationException) {
                // 로그인 취소 시에는 아무것도 하지 않음
            } else {
                openGoogleAccountSetting(context)
            }
        }
    }

    private suspend fun performGoogleLogin(result: GetCredentialResponse) {
        try {
            val credential = googleLoginRepository.checkCredentialType(result)
            val user = googleLoginRepository.loginWithCredential(credential)
            handleLoginSuccess(user)
        } catch (e: Exception) {
            _loginState.value = LoginState.Failure(e.message)
        }
    }

    private fun openGoogleAccountSetting(context: Context) {
        val intent = Intent(ACTION_ADD_ACCOUNT)
        intent.putExtra(EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        context.startActivity(intent)
    }

    suspend fun kakaoLogin(context: Context) {
        try {
            kakaoLoginRepository.loginWithKakaoAccount(context)
            val user = kakaoLoginRepository.getKakaoUser()
            handleLoginSuccess(user)
        } catch (e: Exception) {
            _loginState.value = LoginState.Failure(e.message)
        }
    }

    suspend fun naverLogin(context: Context) {
        try {
            val accessToken = naverLoginRepository.loginWithNaverAccount(context)
            val user = naverLoginRepository.getNaverUser(accessToken)
            handleLoginSuccess(user)
        } catch (e: Exception) {
            _loginState.value = LoginState.Failure(e.message)
        }
    }

    private suspend fun handleLoginSuccess(user: User) {
        storeRepository.saveAccountInfo(user)
        _loginState.value = LoginState.Success(user.email)
    }

    fun initLoginResult() {
        _loginState.value = LoginState.Init
    }
}
