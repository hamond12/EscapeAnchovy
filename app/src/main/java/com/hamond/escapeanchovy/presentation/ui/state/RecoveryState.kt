package com.hamond.escapeanchovy.presentation.ui.state

sealed class RecoveryState {
    data object Init : RecoveryState()
    data object EmailLoading : RecoveryState()
    data object EmailVerified : RecoveryState()
    data object FindEmail : RecoveryState()
    data object ResetPw : RecoveryState()
    data object Failure : RecoveryState()
    data class Error(val error: String?) : RecoveryState()
}