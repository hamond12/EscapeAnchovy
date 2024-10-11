package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider(width: Int, color: Color, topPadding: Int = 0){
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier
            .width(width.dp)
            .padding(top = topPadding.dp)
    )
}