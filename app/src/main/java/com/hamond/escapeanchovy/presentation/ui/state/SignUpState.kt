package com.hamond.escapeanchovy.presentation.ui.state

sealed interface SignUpState<out T> {
    data object Init : SignUpState<Nothing>
    data object Loading : SignUpState<Nothing>
    data class Failure(val error: String?) : SignUpState<Nothing>
    data object EmailVerified : SignUpState<Nothing>
    data object SignUp: SignUpState<Nothing>
}