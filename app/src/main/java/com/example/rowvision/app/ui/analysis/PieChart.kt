// PieChart.kt
package com.example.rowvision.app.ui.analysis

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text

@Composable
fun PieChart(
    data: List<Float>,
    colors: List<Color>,
    centerText: String,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 20.dp,
    centerTextStyle: androidx.compose.ui.text.TextStyle,
    centerTextColor: Color
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val total = data.sum().takeIf { it > 0f } ?: 1f
            var startAngle = -90f
            data.zip(colors).forEach { (percent, color) ->
                val sweep = 360f * (percent / total)
                drawArc(
                    color       = color,
                    startAngle  = startAngle,
                    sweepAngle  = sweep,
                    useCenter   = false,
                    style       = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                startAngle += sweep
            }
        }
        Text(
            text      = centerText,
            style     = centerTextStyle,
            color     = centerTextColor,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

