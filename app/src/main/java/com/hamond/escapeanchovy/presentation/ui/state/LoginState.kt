package com.hamond.escapeanchovy.presentation.ui.state

sealed class LoginState {
    data object Init : LoginState()
    data class Success(val email: String) : LoginState()
    data object Failure : LoginState()
    data class Error(val error: String?) : LoginState()
}