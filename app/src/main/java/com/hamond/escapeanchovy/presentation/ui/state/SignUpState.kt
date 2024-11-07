package com.hamond.escapeanchovy.presentation.ui.state

sealed class SignUpState {
    data object Init : SignUpState()
    data object EmailLoading : SignUpState()
    data object NameLoading : SignUpState()
    data object EmailVerified : SignUpState()
    data object NameVerified: SignUpState()
    data object SignUp: SignUpState()
    data class Error(val error: String?) : SignUpState()
}