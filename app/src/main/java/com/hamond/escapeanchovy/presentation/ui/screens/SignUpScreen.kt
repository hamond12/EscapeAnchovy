package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedButton
import com.hamond.escapeanchovy.presentation.ui.components.OutlinedTextField
import com.hamond.escapeanchovy.ui.theme.LightModeColor
import com.hamond.escapeanchovy.ui.theme.b2_regular
import com.hamond.escapeanchovy.ui.theme.b4_regular
import com.hamond.escapeanchovy.ui.theme.caption1
import com.hamond.escapeanchovy.ui.theme.h3_bold


@Composable
fun SignUpScreen(navController: NavHostController) {
    val focusManager = LocalFocusManager.current
    //val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp)
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "회원가입", style = h3_bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "데이터 백업을 위해 회원가입을 진행해주세요.",
                style = b2_regular.copy(color = LightModeColor.subText)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "이메일", style = b4_regular)
                Spacer(modifier = Modifier.width(4.dp))
                SignUpEmailValidationMessage(text = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(3f)) {
                    SignUpEmailTextField(email = email, onValueChange = { email = it })
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    SignUpEmailValidationButton(onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "이름", style = b4_regular)
                Spacer(modifier = Modifier.width(4.dp))
                SignUpNameValidationMessage(text = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            SignUpNameTextField(name = name, onValueChange = { name = it })
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "비밀번호", style = b4_regular)
                Spacer(modifier = Modifier.width(4.dp))
                SignUpPasswordValidationMessage(text = "")
            }
            Spacer(modifier = Modifier.height(8.dp))
            SignUpPasswordTextField(password = password, onValueChange = { password = it })
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "비밀번호 확인", style = b4_regular)
                Spacer(modifier = Modifier.width(4.dp))
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
                Button(
                    text = "회원가입",
                    onClick = { /*TODO*/ },
                    backgroundColor = LightModeColor.skyblue
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    text = "가입 취소",
                    onClick = { /*TODO*/ },
                    backgroundColor = LightModeColor.orange
                )
            }
        }
    }
}

@Composable
fun SignUpEmailValidationMessage(text:String){
    Text(text = text, style = caption1.copy(color = LightModeColor.error))
}

@Composable
fun SignUpEmailTextField(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onValueChange(it) },
        hint = "이메일 입력"
    )
}

@Composable
fun SignUpEmailValidationButton(onClick: () -> Unit){
    OutlinedButton(onClick = {}, text = "인증 요청")
}

@Composable
fun SignUpNameValidationMessage(text:String){
    Text(text = text, style = caption1.copy(color = LightModeColor.error))
}

@Composable
fun SignUpNameTextField(name: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = { onValueChange(it) },
        maxLength = 10,
        hint = "이름 입력 (10자 제한)"
    )
}

@Composable
fun SignUpPasswordValidationMessage(text:String){
    Text(text = text, style = caption1.copy(color = LightModeColor.error))
}

@Composable
fun SignUpPasswordTextField(password: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onValueChange(it) },
        hint = "비밀번호 입력 (문자, 숫자, 특수문자 포함 8~20자)",
        isPassword = true
    )
}

@Composable
fun SignUpPasswordCheckValidationMessage(text:String){
    Text(text = text, style = caption1.copy(color = LightModeColor.error))
}

@Composable
fun SignUpPasswordCheckTextField(passwordCheck: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = passwordCheck,
        onValueChange = { onValueChange(it) },
        hint = "비밀번호 재입력 ",
        isPassword = true,
        isLast = true
    )
}


@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(rememberNavController())
}