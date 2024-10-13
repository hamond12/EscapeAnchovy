package com.hamond.escapeanchovy.presentation.ui.screens

import android.content.Intent
import android.provider.Settings.ACTION_ADD_ACCOUNT
import android.provider.Settings.EXTRA_ACCOUNT_TYPES
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.presentation.ui.components.CustomAlertDialog
import com.hamond.escapeanchovy.presentation.ui.components.CustomButton
import com.hamond.escapeanchovy.presentation.ui.components.CustomCheckbox
import com.hamond.escapeanchovy.presentation.ui.components.CustomTextField
import com.hamond.escapeanchovy.presentation.ui.components.Divider
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.viewmodel.LogInViewModel
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b3_bold
import com.hamond.escapeanchovy.ui.theme.b3_regular
import com.hamond.escapeanchovy.ui.theme.b4_regular
import com.hamond.escapeanchovy.ui.theme.h1_bold
import com.hamond.escapeanchovy.utils.AccountUtils.saveUserEmail
import com.hamond.escapeanchovy.utils.AccountUtils.setAutoLogin
import com.hamond.escapeanchovy.utils.CommonUtils.showToast
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LogInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val loginResult by loginViewModel.loginResult.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isSocialLogin by remember { mutableStateOf(true) }
    var isAutoLogin by remember { mutableStateOf(false) }

    var isGoogleAccountSettingDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            if (result.isSuccess) {
                if (isSocialLogin || isAutoLogin) setAutoLogin(context)
                saveUserEmail(context, result.getOrNull()!!)
                loginViewModel.initLoginResult()
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            } else {
                showToast(context, "로그인 에러가 발생하였습니다.")
                Log.e("LoginError", "${result.exceptionOrNull()}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 55.dp, end = 55.dp),
        ) {
            Spacer(modifier = Modifier.size(60.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Svg(drawableId = R.drawable.logo, size = 90)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "ESCAPE\nANCHOVY", style = h1_bold)
                Spacer(modifier = Modifier.size(16.dp))
            }

            Spacer(modifier = Modifier.size(60.dp))

            EmailTextField(email = email, onEmailChange = { email = it })

            Spacer(modifier = Modifier.size(20.dp))

            PasswordTextField(password = password, onPasswordChange = { password = it })

            Spacer(modifier = Modifier.size(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoLoginCheckbox(isChecked = isAutoLogin, onCheckedChange = { isAutoLogin = it })
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "이메일 찾기 / 비밀번호 재설정",
                        style = b4_regular.copy(
                            color = LightThemeColor.subText,
                        )
                    )
                    Divider(width = 142, color = LightThemeColor.subText, topPadding = 2)
                }
            }

            Spacer(modifier = Modifier.size(40.dp))

            LoginButton(
                onClick = {
                    // 로그인 처리 로직
                }
            )

            Spacer(modifier = Modifier.size(20.dp))

            SignUpButton(
                onClick = {
                    // 회원가입 처리 로직
                }
            )
        }

        Spacer(modifier = Modifier.size(60.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(width = 80, color = LightThemeColor.hint)
            Row {
                Text(text = "SNS 계정", style = b3_bold)
                Text(text = "으로 ", style = b3_regular)
                Text(text = "간편 ", style = b3_bold)
                Text(text = "로그인", style = b3_regular)
            }
            Divider(width = 80, color = LightThemeColor.hint)
        }

        Spacer(modifier = Modifier.size(40.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            GoogleLoginButton(onClick = {
                coroutineScope.launch {
                    loginViewModel.googleLogin(
                        onFailure = { isGoogleAccountSettingDialogOpen = true }
                    )
                }
            })
            GoogleAccountSettingDialog(
                isOpen = isGoogleAccountSettingDialogOpen,
                onDismiss = { isGoogleAccountSettingDialogOpen = false },
                onConfirm = {
                    val intent = Intent(ACTION_ADD_ACCOUNT)
                    intent.putExtra(EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                    context.startActivity(intent)
                })

            Spacer(modifier = Modifier.size(40.dp))

            KakaoLoginButton(onClick = {
                //loginViewModel.kakaoLogin()
            })

            Spacer(modifier = Modifier.size(40.dp))

            NaverLoginButton(onClick = {})
        }

        Spacer(modifier = Modifier.size(40.dp))


    }
}


@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSimpleScreen() {
    LoginScreen(rememberNavController(), viewModel())
}

@Composable
fun EmailTextField(email: String, onEmailChange: (String) -> Unit) {
    CustomTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        drawableId = R.drawable.ic_email,
        placeholder = "이메일 입력",
    )
}

@Composable
fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit) {
    CustomTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        drawableId = R.drawable.ic_password,
        placeholder = "비밀번호 입력",
        isPassword = true
    )
}

@Composable
fun AutoLoginCheckbox(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckbox(
            isChecked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "자동 로그인", style = b4_regular.copy(LightThemeColor.subText))
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    CustomButton(
        text = "로그인",
        onClick = onClick,
        backgroundColor = LightThemeColor.skyblue
    )
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    CustomButton(
        text = "회원가입",
        onClick = onClick,
        backgroundColor = LightThemeColor.orange
    )
}

@Composable
fun GoogleLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_google, size = 50, onClick = onClick)
}

@Composable
fun GoogleAccountSettingDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (isOpen) {
        CustomAlertDialog(
            title = "계정 추가 필요",
            text = "로그인을 위해 Google 계정을 추가하세요.",
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
fun KakaoLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_kakao, size = 50, onClick = onClick)
}

@Composable
fun NaverLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_naver, size = 50, onClick = onClick)
}
