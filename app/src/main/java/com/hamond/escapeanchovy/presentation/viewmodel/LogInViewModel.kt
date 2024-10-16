package com.hamond.escapeanchovy.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.Settings.ACTION_ADD_ACCOUNT
import android.provider.Settings.EXTRA_ACCOUNT_TYPES
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.AndroidViewModel
import com.hamond.escapeanchovy.data.model.User
import com.hamond.escapeanchovy.data.repository.googleLogin.GoogleLoginRepository
import com.hamond.escapeanchovy.data.repository.kakaoLogin.KakaoLoginRepository
import com.hamond.escapeanchovy.data.repository.store.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    application: Application,
    private val googleLoginRepository: GoogleLoginRepository,
    private val kakaoLoginRepository: KakaoLoginRepository,
    private val storeRepository: StoreRepository
) : AndroidViewModel(application) {

    private val context: Context by lazy { application.applicationContext }

    private val _loginResult = MutableStateFlow<Result<String>?>(null)
    val loginResult: StateFlow<Result<String?>?> get() = _loginResult

    fun openGoogleAccountSetting(){
        val intent = Intent(ACTION_ADD_ACCOUNT)
        intent.putExtra(EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    suspend fun googleLogin(
        onFailure: () -> Unit
    ) {
        val credentialManager = CredentialManager.create(context)
        val request = googleLoginRepository.createCredentialRequest()

        runCatching {
            credentialManager.getCredential(request = request, context = context)
        }.onSuccess {
            performGoogleLogin(it)
        }.onFailure {
            onFailure()
        }
    }

    private suspend fun performGoogleLogin(result: GetCredentialResponse) {
        try {
            val credential = googleLoginRepository.checkCredentialType(result) // 자격 증명 확인
            val user = googleLoginRepository.signInWithCredential(credential) // 자격 증명으로 로그인
            handleLoginSuccess(user)
        } catch (e: Exception) {
            _loginResult.value = Result.failure(e)
        }
    }

    suspend fun kakaoLogin() {
        try {
            kakaoLoginRepository.loginWithKakaoAccount(context)
            val user = kakaoLoginRepository.getKakaoUser()
            handleLoginSuccess(user)
        } catch (e: Exception) {
            _loginResult.value = Result.failure(e)
        }
    }

    private suspend fun handleLoginSuccess(user: User){
        storeRepository.saveAccountInfo(user) // 계정 정보 저장
        _loginResult.value = Result.success(user.email) // 로그인 결과 성공 처리
    }

    fun initLoginResult() {
        _loginResult.value = null
    }
}
