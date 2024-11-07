package com.hamond.escapeanchovy.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamond.escapeanchovy.data.model.User
import com.hamond.escapeanchovy.data.repository.auth.AuthRepository
import com.hamond.escapeanchovy.data.repository.store.StoreRepository
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.getUserEmail
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.saveUserEmail
import com.hamond.escapeanchovy.presentation.ui.state.SignUpState
import com.hamond.escapeanchovy.utils.AccountUtils.formatTime
import com.hamond.escapeanchovy.utils.AccountUtils.hashPw
import com.hamond.escapeanchovy.utils.AccountUtils.isValidEmail
import com.hamond.escapeanchovy.utils.AccountUtils.isValidName
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
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Init)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    private val _emailValidation = mutableStateOf("")
    val emailValidation: State<String> = _emailValidation

    private val _nameValidation = mutableStateOf("")
    val nameValidation: State<String> = _nameValidation

    private val _pwValidation = mutableStateOf("")
    val pwValidation: State<String> = _pwValidation

    private val _pwCheckValidation = mutableStateOf("")
    val pwCheckValidation: State<String> = _pwCheckValidation

    private val maxTime = 180

    private var isEmailVerified = false
    private var isNameVerified = false

    private var isPasswordValid = false
    private var isPasswordCheckValid = false

    suspend fun emailValidationBtnAction(email: String) {
        when {
            email.isBlank() -> {
                _emailValidation.value = "이메일을 입력해주세요."
            }

            !isValidEmail(email) -> {
                _emailValidation.value = "올바른 이메일 형식이 아닙니다."
            }

            isValidEmail(email) -> {
                _signUpState.value = SignUpState.EmailLoading
                checkEmailDuplicated(email)
            }
        }
    }

    private suspend fun checkEmailDuplicated(email: String) {
        try {
            val isEmailDuplicated = storeRepository.isEmailDuplicate(email)
            if (!isEmailDuplicated) {
                _emailValidation.value = "이메일 인증 요청을 보내는 중입니다."
                sendEmailVerification(email)
                saveUserEmail(context, email)
                checkEmailVerification()
            } else {
                _signUpState.value = SignUpState.Init
                _emailValidation.value = "이미 사용 중인 이메일입니다."
            }
        } catch (e: Exception) {
            _signUpState.value = SignUpState.Error(e.message)
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
                var timerRun = true

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
                _signUpState.value = SignUpState.EmailVerified
                _emailValidation.value = "이메일 인증이 완료되었습니다."
            }
        }
    }

    suspend fun validateName(name: String) {
        when {
            name.isBlank() -> {
                _nameValidation.value = "이름을 입력해주세요."
            }

            !isValidName(name) -> {
                _nameValidation.value = "이름엔 특수문자를 포함할 수 없습니다."
            }

            else -> {
                _signUpState.value = SignUpState.NameLoading
                checkNameDuplicated(name)
            }
        }
    }

    private suspend fun checkNameDuplicated(name: String) {
        try {
            val isNameDuplicated = storeRepository.isNameDuplicate(name)

            if (isNameDuplicated) {
                _signUpState.value = SignUpState.Init
                _nameValidation.value = "이미 사용 중인 이름입니다."
            } else {
                _signUpState.value = SignUpState.NameVerified
                _nameValidation.value = "사용 가능한 이름입니다."
                isNameVerified = true
            }
        } catch (e: Exception) {
            _signUpState.value = SignUpState.Error(e.message)
        }
    }

    suspend fun signUpSubmitBtnAction(
        email: String,
        name: String,
        pw: String,
        pwCheck: String
    ) {
        if (isEmailVerified && isNameVerified) {
            if (pw.isEmpty()) {
                _pwValidation.value = "비밀번호를 입력해 주세요."
            } else if (!isValidPw(pw)) {
                _pwValidation.value = "비밀번호 형식이 올바르지 않습니다."
            } else {
                _pwValidation.value = ""
                isPasswordValid = true
            }
        }

        if (isEmailVerified && isNameVerified && isPasswordValid) {
            if (pw != pwCheck) {
                _pwCheckValidation.value = "비밀번호가 일치하지 않습니다."
            } else {
                _pwCheckValidation.value = ""
                isPasswordCheckValid = true
            }
        }

        if (isEmailVerified && isNameVerified && isPasswordValid && isPasswordCheckValid) {
            val user = User(email, name, hashPw(pw))
            saveAccountInfo(user)
            _signUpState.value = SignUpState.SignUp
        }
    }

    private suspend fun saveAccountInfo(user: User) {
        try {
            storeRepository.saveAccountInfo(user)
        } catch (e: Exception) {
            _signUpState.value = SignUpState.Error(e.message)
        }
    }

    fun deleteTempAccount() {
        val inputEmail = getUserEmail(context)
        if (inputEmail != null) {
            try {
                authRepository.loginTempAccount(inputEmail)
                authRepository.deleteTempAccount()
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message)
            }
        }
    }

    fun initSignUpResult() {
        _signUpState.value = SignUpState.Init
    }
}