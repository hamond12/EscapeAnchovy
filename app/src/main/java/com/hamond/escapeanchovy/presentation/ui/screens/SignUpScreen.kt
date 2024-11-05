package com.hamond.escapeanchovy.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.constants.Routes.SIGN_UP
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.presentation.ui.screens.util.ContentResizingScreen
import com.hamond.escapeanchovy.presentation.ui.state.SignUpState
import com.hamond.escapeanchovy.presentation.viewmodel.SignUpViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavHostController) {
    // 뷰모델 관련 변수 선언
    val coroutineScope = rememberCoroutineScope()
    val signUpViewModel = hiltViewModel<SignUpViewModel>()

    // 비동기 작업 진행 여부
    var isEmailLoading by remember { mutableStateOf(false) }
    var isNameLoading by remember { mutableStateOf(false) }

    // 비동기 작업 완료 여부
    var isEmailVerified by remember { mutableStateOf(false) }
    var isNameVerified by remember { mutableStateOf(false) }

    // 사용자 입력값들
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }

    // 회원가입 버튼 활성화 조건
    val signUpEnable =
        isEmailVerified && isNameVerified && password.isNotEmpty() && passwordCheck.isNotEmpty()

    // 회원가입 상태별 동작 정의
    LaunchedEffect(Unit) {
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
                    // 경로 인자를 회원가입으로 설정 후 완료 화면으로 라우팅
                    signUpViewModel.initSignUpResult()
                    navController.navigate("$COMPLETE/sign_up")
                }

                // 에러 발생 시
                is SignUpState.Error -> {
                    Log.e("SignUp", "${signUpState.error}")
                }
            }
        }
    }

    ContentResizingScreen(contentColumn = {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "회원가입",
            style = CustomTheme.typography.h3Bold.copy(color = CustomTheme.colors.text)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "데이터 백업을 위해 회원가입을 진행해주세요.",
            style = CustomTheme.typography.b2Regular.copy(color = CustomTheme.colors.subText)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "이메일",
                style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SignUpEmailValidationMessage(
                text = signUpViewModel.emailValidation.value, isEmailVerified = isEmailVerified
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(3f)) {
                SignUpEmailTextField(
                    email = email,
                    onValueChange = { email = it },
                    enabled = !isEmailLoading && !isEmailVerified
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                SignUpEmailValidationButton(
                    onClick = {
                        coroutineScope.launch { signUpViewModel.validateEmail(email) }
                    }, enabled = !isEmailLoading && !isEmailVerified
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "이름",
                style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SignUpNameValidationMessage(
                text = signUpViewModel.nameValidation.value, isNameVerified = isNameVerified
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(3f)) {
                SignUpNameTextField(
                    name = name,
                    onValueChange = { name = it },
                    enabled = !isNameLoading && !isNameVerified
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                SignUpNameDuplicateCheckButton(
                    onClick = {
                        coroutineScope.launch {
                            signUpViewModel.validateName(name)
                        }
                    }, enabled = !isNameLoading && !isNameVerified
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "비밀번호",
                style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SignUpPasswordValidationMessage(text = signUpViewModel.passwordValidation.value)
        }
        Spacer(modifier = Modifier.height(8.dp))
        SignUpPasswordTextField(password = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "비밀번호 확인",
                style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SignUpPasswordCheckValidationMessage(text = signUpViewModel.passwordCheckValidation.value)
        }
        Spacer(modifier = Modifier.height(8.dp))
        SignUpPasswordCheckTextField(passwordCheck = passwordCheck,
            onValueChange = { passwordCheck = it })
    }, bottomRow = {
        Box(modifier = Modifier.weight(1f)) {
            SignUpCancelButton(onClick = {
                signUpViewModel.deleteTempAccount()
                navController.popBackStack()
            })
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            SignUpSubmitButton(
                onClick = {
                    coroutineScope.launch {
                        signUpViewModel.signUpSubmitBtnAction(
                            email = email,
                            name = name,
                            password = password,
                            passwordCheck = passwordCheck
                        )
                    }
                }, enabled = signUpEnable
            )
        }
    })
}

@Composable
fun SignUpEmailValidationMessage(text: String, isEmailVerified: Boolean) {
    val colors = CustomTheme.colors
    val textColor = if (isEmailVerified) colors.check else colors.error
    Text(
        text = text, style = CustomTheme.typography.caption1.copy(color = textColor)
    )
}

@Composable
fun SignUpEmailTextField(email: String, onValueChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = email, onValueChange = { onValueChange(it) }, hint = "이메일 입력", enabled = enabled
    )
}

@Composable
fun SignUpEmailValidationButton(onClick: () -> Unit, enabled: Boolean) {
    OutlinedButton(
        onClick = onClick, text = "인증 요청", color = CustomTheme.colors.skyBlue, enabled = enabled
    )
}

@Composable
fun SignUpNameValidationMessage(text: String, isNameVerified: Boolean) {
    Text(
        text = text, style = CustomTheme.typography.caption1.copy(
            color = if (isNameVerified) CustomTheme.colors.check else CustomTheme.colors.error
        )
    )
}

@Composable
fun SignUpNameTextField(name: String, onValueChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = name,
        onValueChange = { onValueChange(it) },
        maxLength = 10,
        hint = "이름 입력 (10자 제한)",
        enabled = enabled
    )
}

@Composable
fun SignUpNameDuplicateCheckButton(onClick: () -> Unit, enabled: Boolean) {
    OutlinedButton(
        onClick = onClick, text = "중복 확인", color = CustomTheme.colors.orange, enabled = enabled
    )
}

@Composable
fun SignUpPasswordValidationMessage(text: String) {
    Text(
        text = text, style = CustomTheme.typography.caption1.copy(color = CustomTheme.colors.error)
    )
}

@Composable
fun SignUpPasswordTextField(password: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onValueChange(it) },
        hint = "비밀번호 입력 (문자, 숫자, 특수문자 포함 8~20자)",
        isPassword = true
    )
}

@Composable
fun SignUpPasswordCheckValidationMessage(text: String) {
    Text(
        text = text, style = CustomTheme.typography.caption1.copy(color = CustomTheme.colors.error)
    )
}

@Composable
fun SignUpPasswordCheckTextField(passwordCheck: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = passwordCheck,
        onValueChange = { onValueChange(it) },
        hint = "비밀번호 재입력 ",
        isPassword = true,
        isLast = true
    )
}

@Composable
fun SignUpSubmitButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        text = "회원가입", onClick = onClick, background = CustomTheme.colors.skyBlue, enabled = enabled
    )
}

@Composable
fun SignUpCancelButton(onClick: () -> Unit) {
    Button(
        text = "가입 취소", onClick = onClick, background = CustomTheme.colors.orange
    )
}


