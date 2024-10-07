package com.hamond.escapeanchovy.presentation.ui.screens

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

@Composable
fun LoginScreen(navController: NavHostController) {

    //val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
            EmailTextField()
            Spacer(modifier = Modifier.size(20.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.size(26.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AutoLoginCheckbox()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "자동 로그인", style = b4_regular.copy(LightThemeColor.subText))
                }
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
            CustomButton(
                text = "로그인",
                onClick = { },
                backgroundColor = LightThemeColor.skyblue
            )
            Spacer(modifier = Modifier.size(20.dp))
            CustomButton(
                text = "회원가입",
                onClick = { },
                backgroundColor = LightThemeColor.orange
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
            Svg(drawableId = R.drawable.ic_google, size = 50, onClick = {})
            Spacer(modifier = Modifier.size(34.dp))
            Svg(drawableId = R.drawable.ic_kakao, size = 50, onClick = {})
            Spacer(modifier = Modifier.size(34.dp))
            Svg(drawableId = R.drawable.ic_naver, size = 50, onClick = {})
        }
    }
}

@Composable
fun EmailTextField() {
    var text by remember { mutableStateOf("") }
    CustomTextField(
        value = text,
        onValueChange = { text = it },
        drawableId = R.drawable.ic_email,
        placeholder = "이메일 입력",
    )
}

@Composable
fun PasswordTextField() {
    var text by remember { mutableStateOf("") }
    CustomTextField(
        value = text,
        onValueChange = { text = it },
        drawableId = R.drawable.ic_password,
        placeholder = "비밀번호 입력",
        isPassword = true
    )
}

@Composable
fun AutoLoginCheckbox() {
    var isChecked by remember { mutableStateOf(false) }
    CustomCheckbox(
        isChecked = isChecked,
        onCheckedChange = { isChecked = it },
    )
}


@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSimpleScreen() {
    LoginScreen(rememberNavController())
}
