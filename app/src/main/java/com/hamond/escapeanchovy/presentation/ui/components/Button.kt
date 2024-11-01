package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    background: Color,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = CustomTheme.colors.hint,
            containerColor = background
        )
    ) {
        Text(
            style = CustomTheme.typography.b3Regular,
            text = text,
            color = CustomTheme.colors.buttonText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically) // 텍스트 중앙 정렬
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomButton() {
    Button(
        text = "버튼",
        onClick = {},
        background = CustomTheme.colors.skyBlue
    )
}