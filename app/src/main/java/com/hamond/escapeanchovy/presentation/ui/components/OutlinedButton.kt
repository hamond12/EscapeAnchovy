package com.hamond.escapeanchovy.presentation.ui.components

import android.hardware.lights.Light
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.LightModeColor
import com.hamond.escapeanchovy.ui.theme.b4_regular

@Composable
fun OutlinedButton(
    onClick: () -> Unit, text: String
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(48.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp, color = LightModeColor.skyblue),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LightModeColor.background,
            contentColor = LightModeColor.skyblue,
        )
    ) {
        Text(text = text, style = b4_regular.copy(color = LightModeColor.skyblue))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOutlinedButton() {
    OutlinedButton(onClick = {}, text = "인증 요청")
}