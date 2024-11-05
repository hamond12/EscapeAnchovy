package com.hamond.escapeanchovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hamond.escapeanchovy.constants.Routes
import com.hamond.escapeanchovy.constants.Routes.COMPLETE
import com.hamond.escapeanchovy.constants.Routes.HOME
import com.hamond.escapeanchovy.constants.Routes.LOGIN
import com.hamond.escapeanchovy.constants.Routes.SIGN_UP
import com.hamond.escapeanchovy.constants.Routes.USER_SERVICE
import com.hamond.escapeanchovy.data.source.local.AccountDataSource.getAutoLogin
import com.hamond.escapeanchovy.presentation.ui.screens.CompleteScreen
import com.hamond.escapeanchovy.presentation.ui.screens.HomeScreen
import com.hamond.escapeanchovy.presentation.ui.screens.LoginScreen
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpScreen
import com.hamond.escapeanchovy.presentation.ui.screens.UserServiceScreen
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.ui.theme.EscapeAnchovyTheme
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
        composable(route = USER_SERVICE) { UserServiceScreen(navController) }
        composable(
            route = "$COMPLETE/{complete_task}",
            arguments = listOf(navArgument("complete_task") { type = NavType.StringType })
        ) {
            val msg = it.arguments?.getString("complete_task")
            CompleteScreen(navController, msg)
        }
    }
}