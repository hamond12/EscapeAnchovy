package com.hamond.escapeanchovy.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme

object CommonUtils {
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}