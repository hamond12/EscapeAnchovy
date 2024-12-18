package com.hamond.escapeanchovy.presentation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamond.escapeanchovy.ui.theme.CustomTheme

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            block()
            restoreToCount(checkPoint)
        }
    }

    @Composable
    fun TextSwitch(
        modifier: Modifier = Modifier,
        selectedIndex: Int,
        items: List<String>,
        onSelectionChange: (Int) -> Unit
    ) {
        val selectedTextColor = CustomTheme.colors.text
        val buttonColor = CustomTheme.colors.background

        BoxWithConstraints(
            modifier
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xfff3f3f2))
                .padding(4.dp)
        ) {
            if (items.isNotEmpty()) {

                val maxWidth = this.maxWidth
                val tabWidth = maxWidth / items.size

                val indicatorOffset by animateDpAsState(
                    targetValue = tabWidth * selectedIndex,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                    label = "indicator offset"
                )

                // 흰 버튼 그림자 설정
                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffset)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .width(tabWidth)
                        .fillMaxHeight()
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {

                        // 선택된 항목의 글자 설정
                        val padding = 4.dp.toPx()
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                            size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                            color = selectedTextColor,
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                        )

                        drawWithLayer {
                            drawContent()

                            // 흰 버튼 설정
                            drawRoundRect(
                                topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                                size = Size(size.width / 2, size.height),
                                color = buttonColor,
                                cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                                blendMode = BlendMode.SrcOut
                            )
                        }

                    }
                ) {
                    items.forEachIndexed { index, text ->
                        Box(
                            modifier = Modifier
                                .width(tabWidth)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = null,
                                    onClick = {
                                        onSelectionChange(index)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = text,
                                style = CustomTheme.typography.b4Regular.copy(
                                    color = CustomTheme.colors.border
                                )
                            )
                        }
                    }
                }
            }
        }
    }

@Preview
@Composable
private fun TextSwitchTest() {
    val items = remember { listOf("Man", "Woman") }
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column {
        TextSwitch(
            selectedIndex = selectedIndex,
            items = items,
            onSelectionChange = {
                selectedIndex = it
            }
        )
    }
}