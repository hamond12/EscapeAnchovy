package com.hamond.escapeanchovy.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.constants.Routes.HOME
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.RECOVERY
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.saveAutoLogin
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.saveUserEmail
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.Checkbox
import com.hamond.escapeanchovy.presentation.ui.components.Line
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.ui.components.TextField
import com.hamond.escapeanchovy.presentation.ui.state.LoginState
import com.hamond.escapeanchovy.presentation.viewmodel.LoginViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.utils.CommonUtils.showToast
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {

    // 화면 높이가 모자란 경우를 대비
    val scrollState = rememberScrollState()

    // 텍스트필드 사용 후 포커스 초기화를 위해 사용
    val focusManager = LocalFocusManager.current

    // 컴포즈 UI의 컨텍스트를 사용해야 뷰모델 함수가 오류 안남
    val context = LocalContext.current

    // 뷰모델 관련 변수 선언
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val coroutineScope = rememberCoroutineScope()

    // 사용자 입력값들
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    // 자동 로그인 여부 컨트롤 변수
    var isSocialLogin by remember { mutableStateOf(true) }
    var isAutoLogin by remember { mutableStateOf(false) }

    // 로그인 버튼 활성화 조건 > 아이디 비번 입력
    val loginEnable = email.isNotEmpty() && pw.isNotEmpty()

    // 로그인 상태별 동작 정의
    LaunchedEffect(Unit) {
        loginViewModel.loginState.collect { loginState ->
            when (loginState) {

                // 초기 상태
                is LoginState.Init -> {
                    // (아무런 동작을 하지 않음)
                }

                // 로그인 성공 시
                is LoginState.Success -> {

                    // 자동 로그인 설정
                    if (isSocialLogin || isAutoLogin) {
                        saveAutoLogin(context, true)
                    }

                    // 사용자 이메일 저장
                    saveUserEmail(context, loginState.email)

                    // 로그인 상태 초기화
                    loginViewModel.initLoginResult()

                    // 홈 화면으로 라우팅
                    navController.navigate(HOME) {
                        popUpTo(LOGIN) { inclusive = true }
                    }
                }

                // 로그인 실패 시 (유저 정보 불일치)
                is LoginState.Failure -> {
                    // 토스트 메시지를 띄워 사용자에게 로그인 실패를 알림
                    showToast(context, "로그인 정보가 일치하지 않습니다.")
                    loginViewModel.initLoginResult()
                }

                // 비동기 작업 오류 발생 시
                is LoginState.Error -> {
                    // 디버깅을 위한 에러 로그 출력하기
                    Log.e("Login", "${loginState.error}")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 48.dp),
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            LoginAppTitle()
            Spacer(modifier = Modifier.height(48.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                drawableId = R.drawable.ic_email,
                hint = "이메일 입력",
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = pw,
                onValueChange = { pw = it },
                drawableId = R.drawable.ic_pw,
                hint = "비밀번호 입력",
                isPassword = true,
                isLast = true,
                maxLength = 20
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoLoginCheckbox(isChecked = isAutoLogin, onClick = { isAutoLogin = it })
                RecoveryScreenRouteText(onClick = { navController.navigate(RECOVERY) })
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                text = "로그인",
                onClick = {
                    isSocialLogin = false
                    coroutineScope.launch {
                        loginViewModel.login(email, pw)
                    }
                },
                color = CustomTheme.colors.skyBlue,
                enabled = loginEnable
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                text = "회원가입",
                color = CustomTheme.colors.orange,
                onClick = { navController.navigate(Routes.SIGN_UP) }
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        SocialLoginText()
        Spacer(modifier = Modifier.height(40.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            GoogleLoginButton(onClick = {
                coroutineScope.launch {
                    loginViewModel.googleLogin(context)
                }
            })
            Spacer(modifier = Modifier.width(40.dp))
            KakaoLoginButton(onClick = {
                coroutineScope.launch {
                    loginViewModel.kakaoLogin(context)
                }
            })
            Spacer(modifier = Modifier.width(40.dp))
            NaverLoginButton(onClick = {
                coroutineScope.launch {
                    loginViewModel.naverLogin(context)
                }
            })
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun LoginAppTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Svg(
            drawableId = R.drawable.logo,
            size = 92.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "ESCAPE\nANCHOVY",
            style = CustomTheme.typography.h1Bold.copy(
                color = CustomTheme.colors.text
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}


@Composable
fun AutoLoginCheckbox(isChecked: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            isChecked = isChecked,
            onClick = onClick,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "자동 로그인",
            style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.subText)
        )
    }
}

@Composable
fun RecoveryScreenRouteText(onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.End) {
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "이메일 찾기 / 비밀번호 재설정",
            modifier = Modifier.clickable { onClick.invoke() },
            style = CustomTheme.typography.b4Regular.copy(
                color = CustomTheme.colors.subText,
            ),
        )
        Line(width = 140.dp, color = CustomTheme.colors.subText, topPadding = 2)
    }
}

@Composable
fun SocialLoginText() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Line(width = 82.dp, color = CustomTheme.colors.disabled)
        Row {
            Text(
                text = "SNS 계정",
                style = CustomTheme.typography.b3Bold.copy(color = CustomTheme.colors.text)
            )
            Text(
                text = "으로 ",
                style = CustomTheme.typography.b3Regular.copy(color = CustomTheme.colors.text)
            )
            Text(
                text = "간편 ",
                style = CustomTheme.typography.b3Bold.copy(color = CustomTheme.colors.text)
            )
            Text(
                text = "로그인",
                style = CustomTheme.typography.b3Regular.copy(color = CustomTheme.colors.text)
            )
        }
        Line(width = 82.dp, color = CustomTheme.colors.disabled)
    }
}

@Composable
fun GoogleLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_google, size = 50.dp, onClick = onClick)
}

@Composable
fun KakaoLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_kakao, size = 50.dp, onClick = onClick)
}

@Composable
fun NaverLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_naver, size = 50.dp, onClick = onClick)
}
