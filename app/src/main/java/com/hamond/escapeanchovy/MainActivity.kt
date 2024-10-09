package com.hamond.escapeanchovy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamond.escapeanchovy.constants.Routes.HOME
import com.hamond.escapeanchovy.constants.Routes.SIGN_IN
import com.hamond.escapeanchovy.constants.Routes.SIGN_UP
import com.hamond.escapeanchovy.presentation.ui.screens.HomeScreen
import com.hamond.escapeanchovy.presentation.ui.screens.LoginScreen
import com.hamond.escapeanchovy.presentation.ui.screens.SignUpScreen
import com.hamond.escapeanchovy.presentation.viewmodel.SignInViewModel
import com.hamond.escapeanchovy.ui.theme.EscapeAnchovyTheme
import com.hamond.escapeanchovy.utils.AccountUtils.getUid
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
    val uid = getUid(context)

    EscapeAnchovyTheme {
       NavHost(
            navController = navController,
            startDestination = if (uid == null) SIGN_IN else HOME,
        ) {
            composable(route = SIGN_IN) { LoginScreen(navController, signInViewModel) }
            composable(route = SIGN_UP) { SignUpScreen(navController) }
            composable(route = HOME) { HomeScreen(navController) }
        }
    }
}