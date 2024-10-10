package com.hamond.escapeanchovy.presentation.ui.screens

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.constants.ApiKeys.GOOGLE_CLIENT_ID
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.presentation.ui.components.CustomButton
import com.hamond.escapeanchovy.presentation.ui.components.CustomCheckbox
import com.hamond.escapeanchovy.presentation.ui.components.CustomTextField
import com.hamond.escapeanchovy.presentation.ui.components.Divider
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.presentation.viewmodel.SignInViewModel
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b3_bold
import com.hamond.escapeanchovy.ui.theme.b3_regular
import com.hamond.escapeanchovy.ui.theme.b4_regular
import com.hamond.escapeanchovy.ui.theme.h1_bold
import com.hamond.escapeanchovy.utils.AccountUtils.saveUid
import com.hamond.escapeanchovy.utils.AccountUtils.setAutoLogin
import com.hamond.escapeanchovy.utils.CommonUtils.showToast
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, signInViewModel: SignInViewModel) {

    val context = LocalContext.current
    val loginResult by signInViewModel.loginResult.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isSocialLogin by remember { mutableStateOf(true) }
    var isAutoLogin by remember { mutableStateOf(false) }

    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            if (result.isSuccess) {
                if (isSocialLogin || isAutoLogin) setAutoLogin(context)
                saveUid(context, result.getOrNull() ?: "")
                signInViewModel.initLoginResult()
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            } else {
                showToast(context, "로그인 에러가 발생하였습니다.")
                Log.e("LoginError", "${result.exceptionOrNull()}")
            }
        }
    }

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
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                drawableId = R.drawable.ic_email,
                placeholder = "이메일 입력",
            )
            Spacer(modifier = Modifier.size(20.dp))
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                drawableId = R.drawable.ic_password,
                placeholder = "비밀번호 입력",
                isPassword = true
            )
            Spacer(modifier = Modifier.size(26.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomCheckbox(
                        isChecked = isAutoLogin,
                        onCheckedChange = { isAutoLogin = it },
                    )
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
            Svg(drawableId = R.drawable.ic_google, size = 50, onClick = {
                signInViewModel.googleLogin()
            })
            Spacer(modifier = Modifier.size(34.dp))
            Svg(drawableId = R.drawable.ic_kakao, size = 50, onClick = {})
            Spacer(modifier = Modifier.size(34.dp))
            Svg(drawableId = R.drawable.ic_naver, size = 50, onClick = {})
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSimpleScreen() {
    LoginScreen(rememberNavController(), viewModel())
}


@Composable
fun GoogleLoginButton(navController: NavHostController) {
    val context = LocalContext.current
    val corutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    Svg(drawableId = R.drawable.ic_google, size = 50, onClick = {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setServerClientId(GOOGLE_CLIENT_ID).build()

        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        corutineScope.launch {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            handleSignIn(result, navController, context)
        }
    })
}

private fun handleSignIn(
    result: GetCredentialResponse,
    navController: NavHostController,
    context: Context
) {
    val auth = Firebase.auth
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUid(context, auth.currentUser!!.uid)
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        Log.e("GoogleLoginError", "${task.exception}")
                    }
                }
            }
        }
    }
}
