package com.hamond.escapeanchovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.constants.Routes.HOME
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.SIGN_UP
import com.hamond.escapeanchovy.presentation.ui.screens.HomeScreen
import com.hamond.escapeanchovy.presentation.ui.screens.LoginScreen
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpScreen
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.ui.theme.EscapeAnchovyTheme
import com.hamond.escapeanchovy.utils.AccountUtils.getAutoLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = isSystemInDarkTheme()
            window.decorView.setBackgroundResource(if (!darkTheme) R.color.light else R.color.dark)
            EscapeAnchovyTheme {
                Surface(color = CustomTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val autoLogin = getAutoLogin(context)
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (autoLogin) HOME else LOGIN,
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = LOGIN) { LoginScreen(navController) }
        composable(route = SIGN_UP) { SignUpScreen(navController) }
        composable(route = HOME) { HomeScreen(navController) }
    }
}