package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.utils.NoRippleTheme

@Composable
fun Svg(
    drawableId: Int,
    size: Dp = 24.dp,
    startPadding: Dp = 0.dp,
    onClick: () -> Unit = {},
    isIcon:Boolean = false,
    iconColor: Color = CustomTheme.colors.icon
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Image(
            imageVector = ImageVector.vectorResource(drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(size).padding(start = startPadding)
                .clickable(onClick = onClick),
            colorFilter = if(isIcon) ColorFilter.tint(iconColor) else null
        )
    }
}

