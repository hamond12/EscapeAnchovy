package com.hamond.escapeanchovy.presentation.preview

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.presentation.ui.components.CustomButton
import com.hamond.escapeanchovy.presentation.ui.components.CustomCheckbox
import com.hamond.escapeanchovy.presentation.ui.components.CustomTextField
import com.hamond.escapeanchovy.presentation.ui.components.Divider
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b3_bold
import com.hamond.escapeanchovy.ui.theme.b3_regular
import com.hamond.escapeanchovy.ui.theme.b4_regular
import com.hamond.escapeanchovy.ui.theme.h1_bold

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewLoginScreen() {
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

            EmailTextField(email = "", onEmailChange = { })

            Spacer(modifier = Modifier.size(20.dp))

            PasswordTextField(password = "", onPasswordChange = {})

            Spacer(modifier = Modifier.size(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AutoLoginCheckbox(isChecked = false, onCheckedChange = {})
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "이메일 찾기 / 비밀번호 재설정", style = b4_regular.copy(
                            color = LightThemeColor.subText,
                        )
                    )
                    Divider(width = 142, color = LightThemeColor.subText, topPadding = 2)
                }
            }

            Spacer(modifier = Modifier.size(40.dp))

            LoginButton(onClick = {})

            Spacer(modifier = Modifier.size(20.dp))

            SignUpButton(onClick = {})
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
            GoogleLoginButton(onClick = {})

            Spacer(modifier = Modifier.size(40.dp))

            KakaoLoginButton(onClick = {})

            Spacer(modifier = Modifier.size(40.dp))

            NaverLoginButton(onClick = {})
        }

        Spacer(modifier = Modifier.size(40.dp))
    }
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
        text = "로그인", onClick = onClick, backgroundColor = LightThemeColor.skyblue
    )
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    CustomButton(
        text = "회원가입", onClick = onClick, backgroundColor = LightThemeColor.orange
    )
}

@Composable
fun GoogleLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_google, size = 50, onClick = onClick)
}

@Composable
fun KakaoLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_kakao, size = 50, onClick = onClick)
}

@Composable
fun NaverLoginButton(onClick: () -> Unit) {
    Svg(drawableId = R.drawable.ic_naver, size = 50, onClick = onClick)
}

