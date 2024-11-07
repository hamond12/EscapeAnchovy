package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Line(width: Dp, color: Color, topPadding: Int = 0){
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier
            .width(width)
            .padding(top = topPadding.dp)
    )
}