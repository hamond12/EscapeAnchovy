package com.hamond.escapeanchovy.presentation.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.R
import com.hamond.escapeanchovy.presentation.ui.components.Button
import com.hamond.escapeanchovy.presentation.ui.components.Svg
import com.hamond.escapeanchovy.ui.theme.CustomTheme

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun PreviewCompleteScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Svg(
                drawableId = R.drawable.ic_complete,
                size = 32.dp,
                iconColor = CustomTheme.colors.check
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "이름을 입력해\n계정 이메일을 확인하세요.",
                style = CustomTheme.typography.b1Medium,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 28.dp)
        ) {
            Button(
                text = "이메일 찾기",
                onClick = {},
                color = CustomTheme.colors.skyBlue
            )
        }
    }
}