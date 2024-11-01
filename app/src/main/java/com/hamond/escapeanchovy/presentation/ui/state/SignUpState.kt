package com.hamond.escapeanchovy.presentation.ui.state

sealed class SignUpState {
    data object Init : SignUpState()
    data object Loading : SignUpState()
    data class Failure(val error: String?) : SignUpState()
    data object EmailVerified : SignUpState()
    data object SignUp: SignUpState()
}