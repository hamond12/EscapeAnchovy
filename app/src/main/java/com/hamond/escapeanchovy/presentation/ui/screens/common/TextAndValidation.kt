package com.hamond.escapeanchovy.presentation.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun TextAndValidation(
    text: String,
    validation: String,
    isVerified: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = CustomTheme.typography.b4Regular.copy(CustomTheme.colors.text)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = validation, style = CustomTheme.typography.caption1.copy(
                color = if (isVerified) CustomTheme.colors.check else CustomTheme.colors.error
            )
        )
    }
}