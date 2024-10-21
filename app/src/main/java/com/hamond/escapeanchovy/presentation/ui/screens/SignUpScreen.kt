package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b2_regular
import com.hamond.escapeanchovy.ui.theme.b4_regular
import com.hamond.escapeanchovy.ui.theme.caption1
import com.hamond.escapeanchovy.ui.theme.h3_bold

@Composable
fun SignUpScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp),
    ){
        Spacer(modifier = Modifier.size(40.dp))
        Text(text = "회원가입", style = h3_bold)
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = "데이터 백업을 위해 회원가입을 진행해주세요.", style = b2_regular.copy(color = LightThemeColor.subText))
        Spacer(modifier = Modifier.size(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "이메일", style = b4_regular)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "", style = caption1.copy(color = LightThemeColor.error))
        }
        Spacer(modifier = Modifier.size(4.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {

        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewSignUpScreen(){
    SignUpScreen(rememberNavController())
}