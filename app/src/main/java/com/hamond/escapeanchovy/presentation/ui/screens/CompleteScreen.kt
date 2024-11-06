package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.RECOVERY
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.viewmodel.RecoveryViewModel
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun CompleteScreen(
    navController: NavHostController,
    complete: String?,
    recoveryViewModel: RecoveryViewModel? = null
) {

    // 이메일 찾기 관련 변수들
    val userName = recoveryViewModel?.userName?.value
    val userEmail = recoveryViewModel?.userEmail?.value

    // 경로 인자에 따라 변하는 값들
    var explain by remember { mutableStateOf(AnnotatedString("")) }
    var buttonText by remember { mutableStateOf("") }
    var route by remember { mutableStateOf("") }

    // 경로 인자에 따라 설명, 버튼 텍스트, 루트를 초기화
    when (complete) {
        "sign_up" -> {
            explain = buildAnnotatedString {
                append("회원가입이 완료되었습니다.")
            }
            buttonText = "로그인"
            route = LOGIN
        }

        "find_email" -> {
            explain = buildAnnotatedString {
                append("${userName}님의 계정 이메일은\n")
                withStyle(style = SpanStyle(color = CustomTheme.colors.skyBlue)) {
                    append(userEmail)
                }
                append(" 입니다.")
            }
            buttonText = "확인"
            route = RECOVERY
        }

        "reset_password" -> {
            explain = buildAnnotatedString {
                append("비밀번호 재설정이 완료되었습니다.")
            }
            buttonText = "로그인"
            route = LOGIN
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Svg(
                drawableId = R.drawable.ic_complete,
                size = 32.dp,
                iconColor = CustomTheme.colors.check
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = explain,
                style = CustomTheme.typography.b1Medium,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 28.dp)
        ) {
            Button(
                text = buttonText,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(COMPLETE) { inclusive = true }
                    }
                },
                color = CustomTheme.colors.skyBlue
            )
        }
    }
}