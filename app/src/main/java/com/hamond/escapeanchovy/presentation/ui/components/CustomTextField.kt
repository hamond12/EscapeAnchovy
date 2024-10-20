package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.ui.theme.LightThemeColor
import com.hamond.escapeanchovy.ui.theme.b4_regular


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    drawableId: Int,
    placeholder: String,
    isPassword: Boolean = false,
    maxLength: Int = 20
) {

    var isPasswordHidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.padding(start = 4.dp),
                imageVector = ImageVector.vectorResource(drawableId),
                tint = LightThemeColor.icon,
                contentDescription = null,
            )
            BasicTextField(
                value = value,
                textStyle = b4_regular,
                onValueChange = {
                    if (it.length <= maxLength && it.all { char -> !char.isWhitespace() }) {
                        onValueChange(it)
                    }
                },
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = b4_regular.copy(color = LightThemeColor.hint),
                            )
                        }
                        innerTextField()
                    }
                },
                visualTransformation = if (isPassword && isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (isPassword) {
                Icon(
                    imageVector = if (isPasswordHidden) {
                        ImageVector.vectorResource(R.drawable.ic_visibility_off)
                    } else {
                        ImageVector.vectorResource(R.drawable.ic_visibility)
                    },
                    modifier = Modifier
                        .clickable { isPasswordHidden = !isPasswordHidden }
                        .padding(end = 4.dp),
                    tint = LightThemeColor.icon,
                    contentDescription = null,
                )
            }
        }

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF8A848D))
            .padding(start = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextfield() {
    CustomTextField(
        value = "",
        onValueChange = {},
        drawableId = R.drawable.ic_password,
        placeholder = "",
        isPassword = true
    )
}