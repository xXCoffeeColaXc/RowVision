package com.example.rowvision.app.ui.prestart

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.math.abs

private enum class PreStartState {
    WaitingTilt, Countdown
}

@Composable
fun PreStartScreen(
    navController: NavController
) {
    // 2) Observe tilt angle (–90° flat, 0° vertical)
    val tiltAngle by rememberTiltAngle()

    // 3) UI state
    var state by remember { mutableStateOf(PreStartState.WaitingTilt) }
    var seconds by remember { mutableStateOf(3) }
    var consecutiveUpright by remember { mutableStateOf(0) }

    // 4) When tilt goes within ±15° of vertical, go to COUNTDOWN
    LaunchedEffect(tiltAngle, state) {
        if (state == PreStartState.WaitingTilt) {
            // threshold for “vertical”
            val threshold = 15f
            if (abs(tiltAngle) < threshold) {
                consecutiveUpright++
            } else {
                consecutiveUpright = 0
            }
            // once we’ve seen 5 in a row, move to countdown
            if (consecutiveUpright >= 5) {
                state = PreStartState.Countdown
            }
        }
    }

    // 5) When in COUNTDOWN state, tick 3→0 then navigate
    LaunchedEffect(state) {
        if (state == PreStartState.Countdown) {
            while (seconds > 0) {
                delay(1000)
                seconds--
            }
            navController.navigate("video")
        }
    }

    // 6) UI
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(state) {
            PreStartState.WaitingTilt -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ScreenRotation,
                        contentDescription = "Tilt your phone",
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Tilt your phone upright", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = String.format("%.1f°", tiltAngle),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
            PreStartState.Countdown -> {
                Text(
                    text = seconds.toString(),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}
