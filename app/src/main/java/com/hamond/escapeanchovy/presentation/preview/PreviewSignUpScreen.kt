package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpTitleAndExplain
import com.hamond.escapeanchovy.presentation.ui.screens.common.ContentResizingScreen
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextAndValidation
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextFieldAndButton
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun SignUpScreen() {
    ContentResizingScreen(contentColumn = {
        Spacer(modifier = Modifier.height(48.dp))
        SignUpTitleAndExplain()
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "이메일",
            validation = "",
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldAndButton(
            textField = {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    hint = "이메일 입력",
                )
            },
            button = {
                OutlinedButton(
                    onClick = {},
                    text = "인증 요청",
                    color = CustomTheme.colors.skyBlue,
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "이름",
            validation = "",
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFieldAndButton(
            textField = {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    hint = "이름 입력 (10자 제한)",
                    maxLength = 10
                )
            },
            button = {
                OutlinedButton(
                    onClick = {},
                    text = "중복 확인",
                    color = CustomTheme.colors.orange,
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "비밀번호",
            validation = ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            hint = "비밀번호 입력 (영문, 숫자, 특수문자 포함 8~20자)",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(28.dp))
        TextAndValidation(
            text = "비밀번호 확인",
            validation = ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            hint = "비밀번호 재입력",
            isPassword = true,
        )
    }, bottomRow = {
        Box(modifier = Modifier.weight(1f)) {
            Button(
                text = "가입 취소",
                onClick = {},
                color = CustomTheme.colors.orange
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            Button(
                text = "회원가입",
                onClick = {},
                color = CustomTheme.colors.skyBlue
            )
        }
    })
}