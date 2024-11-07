package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.presentation.ui.components.TextSwitch
import com.hamond.escapeanchovy.presentation.ui.screens.Recovery
import com.hamond.escapeanchovy.presentation.ui.screens.common.ContentResizingScreen
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextAndValidation
import com.hamond.escapeanchovy.presentation.ui.screens.common.TextFieldAndButton
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun RecoveryScreen() {
    val items = remember { listOf("이메일 찾기", "비밀번호 재설정") }
    var mode by remember { mutableIntStateOf(Recovery.FIND_EMAIL.mode) }
    // var mode by remember { mutableIntStateOf(Recovery.RESET_PASSWORD.mode) }

    var explain by remember { mutableStateOf("") }
    var btnText by remember { mutableStateOf("") }

    when (mode) {
        Recovery.FIND_EMAIL.mode -> {
            explain = "이름을 입력해\n계정 이메일을 확인하세요."
            btnText = "이메일 찾기"
        }

        Recovery.RESET_PASSWORD.mode -> {
            explain = "이메일을 인증해\n비밀번호 재설정을 진행하세요"
            btnText = "비밀번호 재설정"
        }
    }

    ContentResizingScreen(
        contentColumn = {
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = explain, style = CustomTheme.typography.h3Bold)
            Spacer(modifier = Modifier.height(28.dp))
            TextSwitch(
                selectedIndex = mode,
                items = items,
                onSelectionChange = {}
            )
            Spacer(modifier = Modifier.height(40.dp))

            when (mode) {
                Recovery.FIND_EMAIL.mode -> {
                    Text(text = "이름")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        hint = "이름 입력",
                    )
                }

                Recovery.RESET_PASSWORD.mode -> {
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
                        text = "비밀번호",
                        validation = "",
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
                        validation = "",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        hint = "비밀번호 재입력",
                        isPassword = true
                    )
                }
            }
        },
        bottomRow = {
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    text = "돌아가기",
                    onClick = {},
                    color = CustomTheme.colors.orange
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    text = btnText,
                    onClick = {},
                    color = CustomTheme.colors.skyBlue,
                    enabled = true
                )
            }
        }
    )
}