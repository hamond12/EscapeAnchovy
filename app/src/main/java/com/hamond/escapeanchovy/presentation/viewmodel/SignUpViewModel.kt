package com.hamond.escapeanchovy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {
    private val emailValidation = ""


    private var isEmailVerified = false
    private val maxTime = 180

    private var isNameValid = false
    private var isPasswordValid = false
    private var isPasswordCheckValid = false
}