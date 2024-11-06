package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.TextField
import com.hamond.escapeanchovy.presentation.ui.screens.AutoLoginCheckbox
import com.hamond.escapeanchovy.presentation.ui.screens.GoogleLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.KakaoLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.LoginAppTitle
import com.hamond.escapeanchovy.presentation.ui.screens.NaverLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.RecoveryScreenRouteText
import com.hamond.escapeanchovy.presentation.ui.screens.SocialLoginText
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
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
                value = "",
                onValueChange = {},
                drawableId = R.drawable.ic_email,
                hint = "이메일 입력",
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "",
                onValueChange = {},
                drawableId = R.drawable.ic_pw,
                hint = "비밀번호 입력",
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoLoginCheckbox(isChecked = true, onClick = {})
                RecoveryScreenRouteText(onClick = {})
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                text = "로그인",
                onClick = {},
                color = CustomTheme.colors.skyBlue,
                enabled = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                text = "회원가입",
                color = CustomTheme.colors.orange,
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        SocialLoginText()
        Spacer(modifier = Modifier.height(40.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            GoogleLoginButton(onClick = {})
            Spacer(modifier = Modifier.width(40.dp))
            KakaoLoginButton(onClick = {})
            Spacer(modifier = Modifier.width(40.dp))
            NaverLoginButton(onClick = {})
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}