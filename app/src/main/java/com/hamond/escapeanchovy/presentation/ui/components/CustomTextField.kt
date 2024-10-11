package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
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
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(drawableId),
                tint = LightThemeColor.icon,
                contentDescription = null,
            )
            BasicTextField(
                value = value,
                textStyle = b4_regular,
                onValueChange = {
                    if (it.length <= maxLength) {
                        onValueChange(it)
                    }
                },
                maxLines = 1,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 8.dp)
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
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF8A848D))
        )
    }
}
