package com.hamond.escapeanchovy.presentation.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    text: String,
    color: Color,
    enabled: Boolean = true
) {
    val buttonColor = if (enabled) color else CustomTheme.colors.disabled.copy().copy(alpha = 0.5f)

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp, color = buttonColor),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomTheme.colors.background,
            disabledContainerColor = CustomTheme.colors.background.copy(alpha = 0.5f)
        ),
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = CustomTheme.typography.b4Regular.copy(color = buttonColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedButton() {
    OutlinedButton(
        onClick = {},
        text = "인증 요청",
        color = CustomTheme.colors.skyBlue,
        enabled = false
    )
}