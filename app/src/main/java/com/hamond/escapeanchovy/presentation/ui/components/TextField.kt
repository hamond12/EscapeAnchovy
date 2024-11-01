package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    drawableId: Int,
    hint: String,
    isPassword: Boolean = false,
    isLast: Boolean = false,
    maxLength: Int = 50
) {
    var isPasswordHidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Svg(drawableId = drawableId, startPadding = 4.dp, isIcon = true)
            BasicTextField(
                value = value,
                textStyle = CustomTheme.typography.b4Regular.copy(color = CustomTheme.colors.text),
                onValueChange = {
                    if (it.length <= maxLength && it.all { char -> !char.isWhitespace() }) {
                        onValueChange(it)
                    }
                },
                cursorBrush = SolidColor(CustomTheme.colors.text),
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = CustomTheme.typography.b4Regular.copy(color = CustomTheme.colors.hint),
                            )
                        }
                        innerTextField()
                    }
                },
                visualTransformation = if (isPassword && isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (isLast) ImeAction.Done else ImeAction.Next
                ),
            )
            if (isPassword) {
                Svg(
                    drawableId = if (isPasswordHidden) R.drawable.ic_visibility_off else R.drawable.ic_visibility,
                    onClick = { isPasswordHidden = !isPasswordHidden },
                    size = 20.dp,
                    isIcon = true
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = CustomTheme.colors.border)
            .padding(start = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTextfield() {
    TextField(
        value = "",
        onValueChange = {},
        drawableId = R.drawable.ic_password,
        hint = "힌트 텍스트",
        isPassword = true
    )
}