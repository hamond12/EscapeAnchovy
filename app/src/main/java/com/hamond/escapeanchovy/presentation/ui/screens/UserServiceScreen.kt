package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun UserServiceScreen(navController: NavHostController) {
    var explain by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Column {

        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewUserServiceScreen(){
    UserServiceScreen(rememberNavController())
}