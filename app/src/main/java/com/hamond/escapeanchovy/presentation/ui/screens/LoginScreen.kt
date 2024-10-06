package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.constants.Routes.HOME

@Composable
fun LoginScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {

        }
        
//        Button(onClick = {
//            navController.navigate(HOME) // 홈 화면으로 이동
//        }) {
//            Text(text = "Go to Home")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleScreen() {
    LoginScreen(rememberNavController()) // 미리보기에서 화면 확인 가능
}
