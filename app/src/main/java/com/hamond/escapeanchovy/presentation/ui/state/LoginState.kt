package com.hamond.escapeanchovy.presentation.ui.state

sealed class LoginState {
    data object Init : LoginState()
    data class Failure(val error: String?) : LoginState()
    data class Success(val email: String) : LoginState()
}