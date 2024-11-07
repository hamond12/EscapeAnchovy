package com.hamond.escapeanchovy.presentation.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldAndButton(
    textField: @Composable () -> Unit,
    button: @Composable () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier.weight(3f)) { textField.invoke() }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) { button.invoke() }
    }
}