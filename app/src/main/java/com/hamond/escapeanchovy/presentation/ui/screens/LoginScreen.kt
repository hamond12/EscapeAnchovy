package com.hamond.escapeanchovy.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.collectAsState
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
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.Checkbox
import com.hamond.escapeanchovy.presentation.ui.components.Line
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.ui.components.TextField
import com.hamond.escapeanchovy.presentation.ui.state.LoginState
import com.hamond.escapeanchovy.presentation.viewmodel.LoginViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.utils.AccountUtils.saveUserEmail
import com.hamond.escapeanchovy.utils.AccountUtils.setAutoLogin
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val darkTheme = isSystemInDarkTheme()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val loginViewModel = hiltViewModel<LoginViewModel>()
    val loginState by loginViewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isSocialLogin by remember { mutableStateOf(true) }
    var isAutoLogin by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        loginViewModel.loginState.collect { loginState ->
            when (loginState) {
                is LoginState.Init -> {}
                is LoginState.Failure -> {
                    Log.e("Login", "${loginState.e}")
                }

                is LoginState.Success -> {
                    if (isSocialLogin || isAutoLogin) setAutoLogin(context)
                    saveUserEmail(context, loginState.data)
                    loginViewModel.initLoginResult()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Svg(
                    drawableId = if (!darkTheme) R.drawable.logo else R.drawable.logo_dark,
                    size = 92.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "ESCAPE\nANCHOVY",
                    style = CustomTheme.typography.h1Bold.copy(color = CustomTheme.colors.text)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(48.dp))
            LoginEmailTextField(email = email, onValueChange = { email = it })
            Spacer(modifier = Modifier.height(16.dp))
            LoginPasswordTextField(password = password, onValueChange = { password = it })
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoLoginCheckbox(isChecked = isAutoLogin, onCheckedChange = { isAutoLogin = it })
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "이메일 찾기 / 비밀번호 재설정",
                        style = CustomTheme.typography.b4Regular.copy(
                            color = CustomTheme.colors.subText,
                        ),
                    )
                    Line(width = 140.dp, color = CustomTheme.colors.subText, topPadding = 2)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            LoginButton(
                onClick = {
                    isSocialLogin = false
                },
                enabled = email.isNotBlank() && password.isNotBlank()
            )
            Spacer(modifier = Modifier.height(20.dp))
            SignUpButton(
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
fun LoginEmailTextField(email: String, onValueChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onValueChange(it) },
        drawableId = R.drawable.ic_email,
        hint = "이메일 입력",
    )
}

@Composable
fun LoginPasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = password,
        onValueChange = { onValueChange(it) },
        drawableId = R.drawable.ic_password,
        hint = "비밀번호 입력",
        isPassword = true,
        isLast = true,
        maxLength = 20
    )
}

@Composable
fun AutoLoginCheckbox(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            isChecked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "자동 로그인",
            style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.subText)
        )
    }
}

@Composable
fun LoginButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        text = "로그인",
        onClick = onClick,
        background = CustomTheme.colors.skyBlue,
        enabled = enabled
    )
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(
        text = "회원가입",
        onClick = onClick,
        background = CustomTheme.colors.orange
    )
}

@Composable
fun SocialLoginText(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Line(width = 82.dp, color = CustomTheme.colors.hint)
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
        Line(width = 82.dp, color = CustomTheme.colors.hint)
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
