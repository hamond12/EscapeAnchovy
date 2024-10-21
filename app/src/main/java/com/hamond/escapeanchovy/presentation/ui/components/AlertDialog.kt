package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b2_regular
import com.hamond.escapeanchovy.ui.theme.h2_medium

@Composable
fun AlertDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        containerColor = LightThemeColor.background,
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, style = h2_medium)
        },
        text = {
            Text(text = text, style = b2_regular)
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("확인", style = b2_regular)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소", style = b2_regular)
            }
        },
        modifier = Modifier
            .width(360.dp)
    )
}