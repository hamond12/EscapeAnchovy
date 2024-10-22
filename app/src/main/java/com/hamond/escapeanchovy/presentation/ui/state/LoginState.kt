package com.hamond.escapeanchovy.presentation.ui.state

sealed interface LoginState<out T> {
    data object Init : LoginState<Nothing>
    data class Success(val email: String) : LoginState<String>
    data class Failure(val error: String?) : LoginState<Nothing>
}
