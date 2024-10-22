package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.ui.theme.LightModeColor
import com.hamond.escapeanchovy.ui.theme.b4_regular

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isPassword: Boolean = false,
    isLast: Boolean = false,
    maxLength: Int = 50
) {
    var isPasswordHidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .border(1.dp, LightModeColor.border, shape = RoundedCornerShape(5.dp)) // 테두리 추가
            .background(LightModeColor.background) // 배경 색상 추가
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
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
                    .height(48.dp)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = b4_regular.copy(color = LightModeColor.hint),
                            )
                        }
                        innerTextField()
                    }
                },
                visualTransformation = if (isPassword && isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if(isLast) ImeAction.Done else ImeAction.Next
                ),

            )
            if (isPassword) {
                Svg(
                    drawableId = if (isPasswordHidden) R.drawable.ic_visibility_off else R.drawable.ic_visibility,
                    onClick = { isPasswordHidden = !isPasswordHidden },
                    size = 20
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}


@Preview
@Composable
fun PreviewBorderedTextField() {
    OutlinedTextField("", {}, "힌트 텍스트", isPassword = true)
}
