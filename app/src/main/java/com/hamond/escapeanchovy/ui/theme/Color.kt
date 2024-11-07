package com.hamond.escapeanchovy.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class CustomColorScheme(
    background: Color,
    text: Color,
    subText: Color,
    buttonText: Color,
    icon: Color,
    border: Color,
    hint: Color,
    disabled: Color,
    skyBlue: Color,
    orange: Color,
    error: Color,
    check: Color,
    lightGray: Color
) {
    var background by mutableStateOf(background)
        private set
    var text by mutableStateOf(text)
        private set
    var subText by mutableStateOf(subText)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set
    var icon by mutableStateOf(icon)
        private set
    var border by mutableStateOf(border)
        private set
    var hint by mutableStateOf(hint)
        private set
    var disabled by mutableStateOf(disabled)
        private set
    var skyBlue by mutableStateOf(skyBlue)
        private set
    var orange by mutableStateOf(orange)
        private set
    var error by mutableStateOf(error)
        private set
    var check by mutableStateOf(check)
        private set
    var lightGray by mutableStateOf(lightGray)
        private set
}

val customLightColorScheme by lazy {
    CustomColorScheme(
        background = Color(0xFFF9F9F9),
        text = Color(0xFF2A2A2A),
        subText = Color(0xFF585858),
        buttonText = Color(0xFFFFFFFF),
        icon = Color(0xFF757575),
        border = Color(0xFF8A848D),
        hint = Color(0xFF9F9F9F),
        disabled = Color(0xFF9F9F9F).copy(alpha = 0.5f),
        skyBlue = Color(0xFF00B6EF),
        orange = Color(0xFFFFB020),
        error = Color(0xFFFF5555),
        check = Color(0xFF13BA00),
        lightGray = Color(0xFFE5E5E5)
    )
}

val customDarkColorScheme by lazy {
    CustomColorScheme(
        background = Color(0xFF222329),
        text = Color(0xFFD0D0D0),
        subText = Color(0xFFBCBCBC),
        hint = Color(0xFF9F9F9F),
        disabled = Color(0xFF9F9F9F).copy(alpha = 0.5f),
        icon = Color(0xFFB1B1B1),
        border = Color(0xFF9E98A1),
        buttonText = Color(0xFFFFFFFF),
        skyBlue = Color(0xFF00ACE5),
        orange = Color(0xFFE19214),
        error = Color(0xFFFF5555),
        check = Color(0xFF14A614),
        lightGray = Color(0xFF505050)
    )
}