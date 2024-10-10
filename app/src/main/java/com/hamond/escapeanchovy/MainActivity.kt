package com.hamond.escapeanchovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
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
import com.hamond.escapeanchovy.presentation.viewmodel.SignInViewModel
import com.hamond.escapeanchovy.utils.AccountUtils.getAutoLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(signInViewModel)
        }
    }
}

@Composable
fun MyApp(signInViewModel: SignInViewModel) {

    val navController = rememberNavController()

    val context = LocalContext.current
    val autoLogin = getAutoLogin(context)

    NavHost(
        navController = navController,
        startDestination = if (autoLogin) HOME else LOGIN,
        //enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = LOGIN) { LoginScreen(navController, signInViewModel) }
        composable(route = SIGN_UP) { SignUpScreen(navController) }
        composable(route = HOME) { HomeScreen(navController) }
    }
}