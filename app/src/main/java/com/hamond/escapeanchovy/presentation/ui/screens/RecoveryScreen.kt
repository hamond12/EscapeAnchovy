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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.RECOVERY
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.presentation.ui.components.TextSwitch
import com.hamond.escapeanchovy.presentation.ui.screens.common.ContentResizingScreen
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextAndValidation
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextFieldAndButton
import com.hamond.escapeanchovy.presentation.ui.state.RecoveryState
import com.hamond.escapeanchovy.presentation.viewmodel.RecoveryViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.utils.CommonUtils.showToast
import kotlinx.coroutines.launch

// 복구 모드 정의
enum class Recovery(val mode: Int) {
    FIND_EMAIL(0),
    RESET_PASSWORD(1)
}

@Composable
fun RecoveryScreen(navController: NavHostController, recoveryViewModel: RecoveryViewModel) {

    // 비동기 관련
    val coroutineScope = rememberCoroutineScope()

    // 토스트 메시지 출력을 위해 선언
    val context = LocalContext.current

    // 밸리데이션 메시지 선언

    val emailValidation = recoveryViewModel.emailValidation.value
    val pwValidation = recoveryViewModel.pwValidation.value
    val pwCheckValidation = recoveryViewModel.pwCheckValidation.value

    // TextSwitch의 초기값 설정
    val items = remember { listOf("이메일 찾기", "비밀번호 재설정") }
    var mode by remember { mutableIntStateOf(Recovery.FIND_EMAIL.mode) }

    // 사용자 입력값
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var pwCheck by remember { mutableStateOf("") }

    // 이메일 인증 작업 관련
    var isEmailLoading by remember { mutableStateOf(false) }
    var isEmailVerified by remember { mutableStateOf(false) }

    // mode에 따라 변하는 값들
    var explain by remember { mutableStateOf("") }
    var btnText by remember { mutableStateOf("") }
    var onClick by remember { mutableStateOf({}) }
    var btnEnabled by remember { mutableStateOf(false) }

    // 회원가입 상태별 동작 정의
    LaunchedEffect(Unit) {

        recoveryViewModel.deleteTempAccount()

        recoveryViewModel.recoveryState.collect { recoveryState ->
            when (recoveryState) {

                // 초기 상태 -> 비동기 작업 진행 여부 변수를 초기값으로 초기화
                is RecoveryState.Init -> {
                    isEmailLoading = false
                }

                // 이메일 인증 시도 시
                is RecoveryState.EmailLoading -> {
                    isEmailLoading = true
                }

                // 이메일 인증 완료 시
                is RecoveryState.EmailVerified -> {
                    isEmailLoading = false
                    isEmailVerified = true
                    recoveryViewModel.deleteTempAccount()
                }

                // 이메일 찾기 성공 시
                is RecoveryState.FindEmail -> {
                    recoveryViewModel.initRecoveryResult()
                    val route = "$COMPLETE/find_email"
                    navController.navigate(route)
                }

                // 비밀번호 재설정 성공 시
                is RecoveryState.ResetPw -> {
                    recoveryViewModel.initRecoveryResult()
                    val route = "$COMPLETE/reset_password"
                    navController.navigate(route) {
                        popUpTo(RECOVERY) { inclusive = true }
                    }
                }

                // 이메일 인증 실패 시
                is RecoveryState.Failure -> {
                    showToast(context, "등록된 계정을 찾을 수 없습니다.")
                }

                // 에러 발생 시
                is RecoveryState.Error -> {
                    Log.e("SignUp", "${recoveryState.error}")
                }
            }
        }
    }

    // 화면을 떠날 때
    DisposableEffect(navController){
        onDispose {
            recoveryViewModel.deleteTempAccount()
            recoveryViewModel.initRecoveryState()
        }
    }

    when (mode) {
        Recovery.FIND_EMAIL.mode -> {
            explain = "이름을 입력해\n계정 이메일을 확인하세요."
            btnText = "이메일 찾기"
            onClick = {
                coroutineScope.launch {
                    recoveryViewModel.findEmailBtnAction(name)
                }
            }
            btnEnabled = name.isNotEmpty()
        }

        Recovery.RESET_PASSWORD.mode -> {
            explain = "이메일을 인증해\n비밀번호 재설정을 진행하세요"
            btnText = "비밀번호 재설정"
            onClick = {
                coroutineScope.launch {
                    recoveryViewModel.resetPwBtnAction(email, pw, pwCheck)
                }
            }
            btnEnabled = isEmailVerified && pw.isNotEmpty() && pwCheck.isNotEmpty()
        }
    }

    ContentResizingScreen(
        contentColumn = {
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = explain, style = CustomTheme.typography.h3Bold)
            Spacer(modifier = Modifier.height(28.dp))
            TextSwitch(
                selectedIndex = mode,
                items = items,
                onSelectionChange = { mode = it }
            )
            Spacer(modifier = Modifier.height(40.dp))

            when (mode) {
                Recovery.FIND_EMAIL.mode -> {
                    Text(text = "이름")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        hint = "이름 입력",
                        maxLength = 10,
                        isLastField = true
                    )
                }

                Recovery.RESET_PASSWORD.mode -> {
                    TextAndValidation(
                        text = "계정 이메일",
                        validation = emailValidation,
                        isVerified = isEmailVerified
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextFieldAndButton(
                        textField = {
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                hint = "계정 이메일 입력",
                                enabled = !isEmailLoading && !isEmailVerified
                            )
                        },
                        button = {
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch {
                                        recoveryViewModel.emailValidationBtnAction(email)
                                    }
                                },
                                text = "인증 요청",
                                color = CustomTheme.colors.skyBlue,
                                enabled = !isEmailLoading && !isEmailVerified
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    TextAndValidation(
                        text = "새 비밀번호",
                        validation = pwValidation,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pw,
                        onValueChange = { pw = it },
                        hint = "비밀번호 입력 (영문, 숫자, 특수문자 포함 8~20자)",
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    TextAndValidation(
                        text = "새 비밀번호 확인",
                        validation = pwCheckValidation,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pwCheck,
                        onValueChange = { pwCheck = it },
                        hint = "비밀번호 재입력",
                        isPassword = true,
                        isLastField = true
                    )
                }
            }
        },
        bottomRow = {
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    text = "돌아가기",
                    onClick = {
                        navController.navigate(LOGIN) {
                            popUpTo(RECOVERY) { inclusive = true }
                        }
                    },
                    color = CustomTheme.colors.orange
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    text = btnText,
                    onClick = onClick,
                    color = CustomTheme.colors.skyBlue,
                    enabled = btnEnabled
                )
            }
        }
    )
}