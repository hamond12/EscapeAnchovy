package com.hamond.escapeanchovy.presentation.ui.state

sealed interface LoginState<out T> {
    data object Init : LoginState<Nothing>
    data class Failure(val e: String?) : LoginState<Nothing>
    data class Success<T>(val data: T) : LoginState<T>
}