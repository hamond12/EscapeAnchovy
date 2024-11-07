package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.ui.theme.CustomTheme
import com.hamond.escapeanchovy.utils.NoRippleTheme

@Composable
fun Checkbox(
    isChecked: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkBoxColor = if (isChecked) CustomTheme.colors.skyBlue else CustomTheme.colors.background

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(
            modifier = modifier
                .size(18.dp)
                .clickable { onClick(!isChecked) }
                .background(
                    color = checkBoxColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .then(
                    if (!isChecked) {
                        Modifier.border(1.dp, CustomTheme.colors.disabled, RoundedCornerShape(4.dp))
                    } else {
                        Modifier
                    }
                )
        ) {
            if (isChecked) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    tint = CustomTheme.colors.background,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleScreen() {
    Checkbox(
        isChecked = true,
        onClick = { },
    )
}



