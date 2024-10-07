package com.hamond.escapeanchovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.presentation.ui.screens.HomeScreen
import com.hamond.escapeanchovy.presentation.ui.screens.LoginScreen
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpScreen
import com.hamond.escapeanchovy.ui.theme.EscapeAnchovyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    EscapeAnchovyTheme {
        NavHost(navController = navController, startDestination = Routes.LOGIN) {
            composable(route = Routes.LOGIN) { LoginScreen(navController) }
            composable(route = Routes.SIGNUP) { SignUpScreen(navController) }
            composable(route = Routes.HOME) { HomeScreen(navController) }
        }
    }
}