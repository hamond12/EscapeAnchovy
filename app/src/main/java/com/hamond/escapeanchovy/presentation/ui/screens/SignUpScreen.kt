package com.hamond.escapeanchovy.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.presentation.ui.screens.common.ContentResizingScreen
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextAndValidation
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextFieldAndButton
import com.hamond.escapeanchovy.presentation.ui.state.SignUpState
import com.hamond.escapeanchovy.presentation.viewmodel.SignUpViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavHostController) {

    // 뷰모델 관련 변수 선언
    val coroutineScope = rememberCoroutineScope()
    val signUpViewModel = hiltViewModel<SignUpViewModel>()

    // 밸리데이션 메시지 선언
    val emailValidation = signUpViewModel.emailValidation.value
    val nameValidation = signUpViewModel.nameValidation.value
    val pwValidation = signUpViewModel.pwValidation.value
    val pwCheckValidation = signUpViewModel.pwCheckValidation.value

    // 비동기 작업 진행 여부
    var isEmailLoading by remember { mutableStateOf(false) }
    var isNameLoading by remember { mutableStateOf(false) }

    // 비동기 작업 완료 여부
    var isEmailVerified by remember { mutableStateOf(false) }
    var isNameVerified by remember { mutableStateOf(false) }

    // 사용자 입력값들
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var pwCheck by remember { mutableStateOf("") }

    // 회원가입 버튼 활성화 조건
    val signUpEnable =
        isEmailVerified && isNameVerified && pw.isNotEmpty() && pwCheck.isNotEmpty()

    // 회원가입 상태별 동작 정의
    LaunchedEffect(Unit) {

        // 이메일 인증 요청을 보낸 후 가입 취소 버튼을 누르거나 이메일 인증을 완료하지 않았을 때
        // 생성된 임시계정에 로그인하여 이메일 인증을 보내기 위해 생성한 임시계정을 제거한다.
        signUpViewModel.deleteTempAccount()

        signUpViewModel.signUpState.collect { signUpState ->
            when (signUpState) {

                // 초기 상태 -> 비동기 작업 진행 여부 변수를 초기값으로 초기화
                is SignUpState.Init -> {
                    isEmailLoading = false
                    isNameLoading = false
                }

                // 이메일 인증 시도 시
                is SignUpState.EmailLoading -> {
                    isEmailLoading = true
                }

                // 이름 중복 확인 시
                is SignUpState.NameLoading -> {
                    isNameLoading = true
                }

                // 이메일 인증 완료 시
                is SignUpState.EmailVerified -> {
                    isEmailLoading = false
                    isEmailVerified = true
                    signUpViewModel.deleteTempAccount()
                }

                // 이름 중복 확인 완료 시
                is SignUpState.NameVerified -> {
                    isNameLoading = false
                    isNameVerified = true
                }

                // 회원가입 시
                is SignUpState.SignUp -> {
                    signUpViewModel.initSignUpResult()
                    val route = "$COMPLETE/sign_up"
                    navController.navigate(route)
                }

                // 에러 발생 시
                is SignUpState.Error -> {
                    Log.e("SignUp", "${signUpState.error}")
                }
            }
        }
    }

    DisposableEffect(navController){
        onDispose {
            signUpViewModel.deleteTempAccount()
        }
    }

    ContentResizingScreen(contentColumn = {
        Spacer(modifier = Modifier.height(48.dp))
        SignUpTitleAndExplain()
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "이메일",
            validation = emailValidation,
            isVerified = isEmailVerified
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldAndButton(
            textField = {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    hint = "이메일 입력",
                    enabled = !isEmailLoading && !isEmailVerified
                )
            },
            button = {
                OutlinedButton(
                    onClick = { coroutineScope.launch {
                        signUpViewModel.emailValidationBtnAction(email)
                    }},
                    text = "인증 요청",
                    color = CustomTheme.colors.skyBlue,
                    enabled = !isEmailLoading && !isEmailVerified
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "이름",
            validation = nameValidation,
            isVerified = isNameVerified
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldAndButton(
            textField = {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    hint = "이름 입력 (10자 제한)",
                    maxLength = 10,
                    enabled = !isNameLoading && !isNameVerified,
                )
            },
            button = {
                OutlinedButton(
                    onClick = { coroutineScope.launch { signUpViewModel.validateName(name) } },
                    text = "중복 확인",
                    color = CustomTheme.colors.orange,
                    enabled = !isNameLoading && !isNameVerified
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "비밀번호",
            validation = pwValidation
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = pw,
            onValueChange = { pw = it },
            hint = "비밀번호 입력 (영문, 숫자, 특수문자 포함 8~20자)",
            maxLength = 20,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "비밀번호 확인",
            validation = pwCheckValidation
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = pwCheck,
            onValueChange = { pwCheck = it },
            hint = "비밀번호 재입력",
            maxLength = 20,
            isPassword = true,
            isLastField = true
        )
    }, bottomRow = {
        Box(modifier = Modifier.weight(1f)) {
            Button(
                text = "가입 취소",
                onClick = { navController.popBackStack() },
                color = CustomTheme.colors.orange
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            Button(
                text = "회원가입",
                onClick = {
                    coroutineScope.launch {
                        signUpViewModel.signUpSubmitBtnAction(
                            email = email,
                            name = name,
                            pw = pw,
                            pwCheck = pwCheck
                        )
                    }
                },
                color = CustomTheme.colors.skyBlue,
                enabled = signUpEnable
            )
        }
    })
}

@Composable
fun SignUpTitleAndExplain() {
    Text(
        text = "회원가입",
        style = CustomTheme.typography.h3Bold.copy(color = CustomTheme.colors.text)
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "데이터 백업을 위해 회원가입을 진행해주세요.",
        style = CustomTheme.typography.b2Regular.copy(color = CustomTheme.colors.subText)
    )
}

