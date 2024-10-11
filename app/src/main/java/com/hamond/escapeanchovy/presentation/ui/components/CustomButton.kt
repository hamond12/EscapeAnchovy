package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b3_regular

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    fillMaxWidth: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier.width(120.dp)).height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor
        )
    ) {
        Text(
            style = b3_regular,
            text = text,
            color = LightThemeColor.background,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically) // 텍스트 중앙 정렬
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomButton() {
    CustomButton(text = "버튼", onClick = { /*TODO*/ }, backgroundColor = LightThemeColor.skyblue)
}