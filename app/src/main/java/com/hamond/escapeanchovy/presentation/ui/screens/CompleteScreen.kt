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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.USER_SERVICE
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.presentation.ui.components.Button

@Composable
fun CompleteScreen(navController: NavHostController, complete: String?) {

    var explain by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("") }
    var route by remember { mutableStateOf("") }

    // 경로 인자에 따라 설명, 버튼 텍스트, 루트를 초기화
    when (complete) {
        "sign_up" -> {
            explain = "회원가입이 완료되었습니다."
            buttonText = "로그인"
            route = LOGIN
        }

        "find_email" -> {
            explain = "name님의 계정 이메일은\nemail 입니다."
            buttonText = "확인"
            route = USER_SERVICE
        }

        "reset_password" -> {
            explain = "비밀번호 재설정이 완료되었습니다."
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
                drawableId = R.drawable.ic_check,
                size = 32.dp,
                iconColor = CustomTheme.colors.check
            )
            Spacer(modifier = Modifier.height(28.dp))
            CompleteExplainText(explain = explain)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 28.dp)
        ) {
            CompleteRouteButton(
                buttonText = buttonText,
                navController = navController,
                route = route
            )
        }
    }
}

@Composable
fun CompleteExplainText(explain: String) {
    Text(text = explain, style = CustomTheme.typography.b1Bold)
}

@Composable
fun CompleteRouteButton(buttonText: String, navController: NavHostController, route: String) {
    Button(
        text = buttonText,
        onClick = {
            navController.navigate(route) {
                popUpTo(COMPLETE) { inclusive = true }
            }
        },
        background = CustomTheme.colors.skyBlue
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun asdf() {
    CompleteScreen(navController = rememberNavController(), complete = "sign_up")
}