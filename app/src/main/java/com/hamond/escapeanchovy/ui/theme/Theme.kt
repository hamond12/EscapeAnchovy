package com.hamond.escapeanchovy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalColorScheme = staticCompositionLocalOf { customLightColorScheme }
val LocalTypography = staticCompositionLocalOf { CustomTypography() }

@Composable
fun EscapeAnchovyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = CustomTypography()
    val currentColor = remember {
        if (!darkTheme) {
            customLightColorScheme
        } else {
            customDarkColorScheme
        }
    }

    CompositionLocalProvider(
        LocalColorScheme provides currentColor,
        LocalTypography provides typography
    ) {
        ProvideTextStyle(
            typography.b4Regular,
            content = content
        )
    }
}

object CustomTheme {
    val colors: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}