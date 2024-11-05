package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpCancelButton
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpEmailTextField
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpEmailValidationButton
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpEmailValidationMessage
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpNameDuplicateCheckButton
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpNameTextField
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpNameValidationMessage
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpPasswordCheckTextField
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpPasswordCheckValidationMessage
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpPasswordTextField
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpPasswordValidationMessage
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpSubmitButton
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSignUpScreen() {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(CustomTheme.colors.background)
            .padding(start = 40.dp, end = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "회원가입",
                style = CustomTheme.typography.h3Bold.copy(color = CustomTheme.colors.text)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "데이터 백업을 위해 회원가입을 진행해주세요.",
                style = CustomTheme.typography.b2Regular.copy(color = CustomTheme.colors.subText)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "이메일",
                    style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
                )
                Spacer(modifier = Modifier.width(8.dp))
                SignUpEmailValidationMessage(
                    text = "",
                    isEmailVerified = false
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(3f)) {
                    SignUpEmailTextField(
                        email = email,
                        onValueChange = { email = it },
                        enabled = true
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    SignUpEmailValidationButton(
                        onClick = {}, enabled = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "이름",
                    style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
                )
                Spacer(modifier = Modifier.width(8.dp))
                SignUpNameValidationMessage(
                    text = "",
                    isNameVerified = false
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(3f)) {
                    SignUpNameTextField(
                        name = name,
                        onValueChange = { name = it },
                        enabled = true
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    SignUpNameDuplicateCheckButton(
                        onClick = {}, enabled = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "비밀번호",
                    style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
                )
                Spacer(modifier = Modifier.width(8.dp))
                SignUpPasswordValidationMessage(text = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            SignUpPasswordTextField(password = password, onValueChange = { password = it })
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "비밀번호 확인",
                    style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
                )
                Spacer(modifier = Modifier.width(8 .dp))
                SignUpPasswordCheckValidationMessage(text = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            SignUpPasswordCheckTextField(
                passwordCheck = passwordCheck,
                onValueChange = { passwordCheck = it }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, bottom = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SignUpCancelButton(onClick = {})
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                SignUpSubmitButton(
                    onClick = {}, enabled = true
                )
            }
        }
    }
}