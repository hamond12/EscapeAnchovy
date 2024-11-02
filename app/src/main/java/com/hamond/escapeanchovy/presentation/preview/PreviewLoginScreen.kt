package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.presentation.ui.components.Line
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.ui.screens.AutoLoginCheckbox
import com.hamond.escapeanchovy.presentation.ui.screens.GoogleLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.KakaoLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.LoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.LoginEmailTextField
import com.hamond.escapeanchovy.presentation.ui.screens.LoginPasswordTextField
import com.hamond.escapeanchovy.presentation.ui.screens.NaverLoginButton
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpButton
import com.hamond.escapeanchovy.presentation.ui.screens.SocialLoginText
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewLoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isSocialLogin by remember { mutableStateOf(true) }
    var isAutoLogin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background),
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
            SignUpButton(onClick = {})
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