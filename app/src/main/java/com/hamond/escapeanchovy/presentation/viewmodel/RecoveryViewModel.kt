package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamond.escapeanchovy.data.repository.auth.AuthRepository
import com.hamond.escapeanchovy.data.repository.store.StoreRepository
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.getUserEmail
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.saveUserEmail
import com.hamond.escapeanchovy.presentation.ui.state.RecoveryState
import com.hamond.escapeanchovy.utils.AccountUtils.formatTime
import com.hamond.escapeanchovy.utils.AccountUtils.hashPw
import com.hamond.escapeanchovy.utils.AccountUtils.isValidEmail
import com.hamond.escapeanchovy.utils.AccountUtils.isValidPw
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _recoveryState = MutableStateFlow<RecoveryState>(RecoveryState.Init)
    val recoveryState: StateFlow<RecoveryState> = _recoveryState.asStateFlow()

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _userEmail = mutableStateOf("")
    val userEmail: State<String> = _userEmail

    private val _emailValidation = mutableStateOf("")
    val emailValidation: State<String> = _emailValidation

    private val _pwValidation = mutableStateOf("")
    val pwValidation: State<String> = _pwValidation

    private val _pwCheckValidation = mutableStateOf("")
    val pwCheckValidation: State<String> = _pwCheckValidation

    private val maxTime = 180
    private var timerRun = false
    private var isEmailVerified = false

    private var isPwValid = false
    private var isPwCheckValid = false

    suspend fun emailValidationBtnAction(email: String) {
        when {
            email.isBlank() -> {
                _emailValidation.value = "이메일을 입력해주세요."
            }

            !isValidEmail(email) -> {
                _emailValidation.value = "올바른 이메일 형식이 아닙니다."
            }

            isValidEmail(email) -> {
                _recoveryState.value = RecoveryState.EmailLoading
                checkEmailAccount(email)
            }
        }
    }

    private suspend fun checkEmailAccount(email: String) {
        try {
            val isEmailDuplicated = storeRepository.isEmailDuplicate(email)
            if (isEmailDuplicated) {
                _emailValidation.value = "이메일 인증 요청을 보내는 중입니다."
                sendEmailVerification(email)
                saveUserEmail(context, email)
                checkEmailVerification()
            } else {
                _recoveryState.value = RecoveryState.Init
                _emailValidation.value = "등록된 계정을 찾을 수 없습니다."
            }
        } catch (e: Exception) {
            _recoveryState.value = RecoveryState.Error(e.message)
        }
    }

    private suspend fun sendEmailVerification(email: String) {
        val user = authRepository.createTempAccount(email).user
        authRepository.sendEmailVerification(user)
        _emailValidation.value = "이메일 인증을 진행해주세요."
    }

    private fun checkEmailVerification() {
        viewModelScope.launch {
            val timer = launch {
                val startTime = System.currentTimeMillis()
                timerRun = true

                while (timerRun) {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = ((currentTime - startTime) / 1000).toInt()
                    val remainingTime = maxTime - elapsedTime

                    if (remainingTime <= 0) {
                        timerRun = false
                        _emailValidation.value = "인증 유효 기간이 만료되었습니다."
                    } else {
                        _emailValidation.value = "인증 유효 기간: ${formatTime(remainingTime)}"
                    }
                    delay(1000L)
                }
            }

            launch {
                var pollingRun = true

                while (pollingRun) {
                    val complete = authRepository.checkEmailVerification()
                    if (complete == true) {
                        isEmailVerified = complete
                        pollingRun = false
                        timer.cancel()
                    }
                    delay(2000L)
                }
            }

            timer.join()

            if (isEmailVerified) {
                _recoveryState.value = RecoveryState.EmailVerified
                _emailValidation.value = "이메일 인증이 완료되었습니다."
            }
        }
    }

    suspend fun findEmailBtnAction(name: String) {
        try {
            val email = storeRepository.getEmailByName(name)
            if (email != null) {
                _userName.value = name
                _userEmail.value = email
                _recoveryState.value = RecoveryState.FindEmail
            } else {
                _recoveryState.value = RecoveryState.Failure
            }
        } catch (e: Exception) {
            _recoveryState.value = RecoveryState.Error(e.message)
        }
    }

    suspend fun resetPwBtnAction(
        email: String,
        pw: String,
        pwCheck: String
    ) {
        if (isEmailVerified) {
            if (pw.isEmpty()) {
                _pwValidation.value = "비밀번호를 입력해 주세요."
            } else if (!isValidPw(pw)) {
                _pwValidation.value = "비밀번호 형식이 올바르지 않습니다."
            } else {
                _pwValidation.value = ""
                isPwValid = true
            }
        }

        if (isEmailVerified && isPwValid) {
            if (pw != pwCheck) {
                _pwCheckValidation.value = "비밀번호가 일치하지 않습니다."
            } else {
                _pwCheckValidation.value = ""
                isPwCheckValid = true
            }
        }

        if (isEmailVerified && isPwValid && isPwCheckValid) {
            resetPw(email, pw)
            _recoveryState.value = RecoveryState.ResetPw
        }
    }

    private suspend fun resetPw(email: String, pw: String){
        try {
            storeRepository.resetPwByEmail(email, hashPw(pw))
        }catch (e:Exception){
            _recoveryState.value = RecoveryState.Error(e.message)
        }
    }

    fun deleteTempAccount() {
        val inputEmail = getUserEmail(context)
        if (inputEmail != null) {
            try {
                authRepository.loginTempAccount(inputEmail)
                authRepository.deleteTempAccount()
            } catch (e: Exception) {
                _recoveryState.value = RecoveryState.Error(e.message)
            }
        }
    }

    fun initRecoveryState(){
        _recoveryState.value = RecoveryState.Init
        timerRun = false
        _emailValidation.value = ""
        _pwValidation.value = ""
        _pwCheckValidation.value = ""
    }

    fun initRecoveryResult() {
        _recoveryState.value = RecoveryState.Init
    }
}