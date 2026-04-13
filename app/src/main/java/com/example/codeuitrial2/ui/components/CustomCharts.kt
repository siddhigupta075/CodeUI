package com.example.codeuitrial2.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularBudgetChart(
    percentage: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF1E88E5),
    backgroundColor: Color = Color(0xFF30363D).copy(alpha = 0.5f)
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = modifier.size(140.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(120.dp)) {
            val strokeWidth = 14.dp.toPx()
            
            // Draw background circle
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Draw foreground circle
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedPercentage,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(percentage * 100).toInt()}%",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "of budget",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun SimpleBarChart(
    data: List<Float>, // Heights of bars
    labels: List<String>,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF1E88E5)
) {
    Box(modifier = modifier.fillMaxWidth().height(200.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val maxData = data.maxOrNull() ?: 1f
            val spacing = size.width / (data.size * 2)
            val barWidth = spacing
            
            data.forEachIndexed { index, value ->
                val barHeight = (value / maxData) * size.height * 0.8f // Leave room for labels
                val xOffset = index * (barWidth + spacing) + spacing / 2
                
                drawLine(
                    color = barColor,
                    start = Offset(xOffset, size.height),
                    end = Offset(xOffset, size.height - barHeight),
                    strokeWidth = barWidth,
                    cap = StrokeCap.Round
                )
            }
        }
        
        // Pseudo Labels using fixed row layout could be implemented here
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(top = 180.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
