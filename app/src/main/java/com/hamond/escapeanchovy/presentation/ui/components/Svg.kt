package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.utils.NoRippleTheme

@Composable
fun Svg(
    drawableId: Int,
    size: Int,
    onClick: () -> Unit = {} // 클릭 이벤트를 위한 람다 매개변수 추가
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Image(
            imageVector = ImageVector.vectorResource(drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
                .clickable(onClick = onClick) // 클릭 이벤트 추가
        )
    }
}

