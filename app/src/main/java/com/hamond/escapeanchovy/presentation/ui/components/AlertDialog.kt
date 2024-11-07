package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun AlertDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        containerColor = CustomTheme.colors.background,
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, style = CustomTheme.typography.h2Medium)
        },
        text = {
            Text(text = text, style = CustomTheme.typography.b2Regular)
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("확인", style = CustomTheme.typography.b2Regular)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소", style = CustomTheme.typography.b2Regular)
            }
        },
        modifier = Modifier
            .width(360.dp)
    )
}