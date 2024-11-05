package com.hamond.escapeanchovy.presentation.ui.screens.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun ContentResizingScreen(
    contentColumn: @Composable ColumnScope.() -> Unit,
    bottomRow: @Composable RowScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        // 상단 스크롤 가능한 Column 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            contentColumn()
        }

        // 하단 Row 영역 (버튼 등 위치)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, bottom = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomRow()
        }
    }
}
