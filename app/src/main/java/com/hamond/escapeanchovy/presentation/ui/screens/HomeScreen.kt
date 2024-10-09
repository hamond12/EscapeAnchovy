package com.hamond.escapeanchovy.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.utils.AccountUtils.getUid
import com.hamond.escapeanchovy.utils.AccountUtils.removeUid

@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current

    //val uid = getUid(context)

    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Button(onClick = {
            removeUid(context)
            navController.navigate(Routes.SIGN_IN) {
                popUpTo(Routes.HOME) { inclusive = true }
            }
        }) {
            Text(text = "Log Out")
        }
    }
}