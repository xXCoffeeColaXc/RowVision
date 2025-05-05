package com.example.rowvision.app.ui.video

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.R
import com.example.rowvision.app.data.model.Session
import com.example.rowvision.app.ui.home.HomeViewModel
import com.example.rowvision.app.ui.sessions.SessionsViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedBoxWithConstraintsScope", "DefaultLocale")
@Composable
fun VideoScreen(
    navController: NavController,
    videoVM: VideoViewModel = hiltViewModel(),
    sessionsVm: SessionsViewModel = hiltViewModel()
) {

    val settings by videoVM.settings.collectAsState()
    val plan     = settings.plan
    val interval = settings.interval
    val goal     = settings.goal

    // 2) Kick off a timer
    val startMillis = remember { System.currentTimeMillis() }
    var elapsedMillis by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(1000L)
            elapsedMillis = System.currentTimeMillis() - startMillis
        }
    }

    // 3) Convert to your demo metrics
    val durationSeconds   = (elapsedMillis / 1000L).toInt()
    val distanceMeters    = durationSeconds * 2        // e.g. 2 m per second
    val strokesPerMinute  = 30                         // dummy constant
    val paceSecondsPer500m = if (distanceMeters > 0)
        (durationSeconds * 500) / distanceMeters
    else 0



    // 1) Prepare ExoPlayer (unchanged)
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val rawUri: Uri =
                RawResourceDataSource.buildRawResourceUri(R.raw.session_intro)
            setMediaItem(MediaItem.fromUri(rawUri))
            playWhenReady = true
            prepare()
        }
    }
    DisposableEffect(exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    // 2) Elapsed seconds
    var elapsed by remember { mutableLongStateOf(0L) }
    var strokes by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            elapsed++
            strokes++
        }
    }

    // 3) Motivations list
    val motivations = remember { mutableStateListOf<Long>() }
    LaunchedEffect(Unit) {
        while (true) {
            motivations += elapsed  // use elapsed as unique key
            delay(1000)
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val screenHeight = maxHeight

        // 1) Video stretched full-screen
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier.matchParentSize()
        )

        // 2) Overlay UI
        Box(Modifier.fillMaxSize().padding(24.dp)) {
            // Top row (timer & strokes) — unchanged
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    String.format(
                        "%02d:%02d",
                        TimeUnit.SECONDS.toMinutes(elapsed),
                        elapsed % 60
                    ),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Text(
                    text = "$strokes strokes",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }

            // 3) Motivation texts container
            Box(Modifier.fillMaxSize()) {
                motivations.forEach { key ->
                    // one Animatable for Y offset, one for alpha
                    val offsetY = remember { Animatable(screenHeight, Dp.VectorConverter) }
                    val alpha = remember { Animatable(1f) }

                    LaunchedEffect(key) {
                        // fly from bottom → middle
                        offsetY.animateTo(
                            targetValue = screenHeight / 2,
                            animationSpec = tween(durationMillis = 1500)
                        )
                        // then fade out
                        alpha.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 200, delayMillis = 500)
                        )
                        motivations.remove(key)
                    }

                    // the text itself, in a black transparent pill
                    Box(
                        modifier = Modifier
                            .offset(x = 16.dp, y = offsetY.value)
                            .alpha(alpha.value)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "Motivate!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }

            // 4) Stop button — unchanged except size if you like
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        // build and save the session
                        val now = LocalDateTime.now()
                        val session = Session(
                            id                   = sessionsVm.nextId(),
                            type                 = plan,
                            dateTime             = now,
                            durationSeconds      = durationSeconds,
                            distanceMeters       = distanceMeters,
                            strokesPerMinute     = strokesPerMinute,
                            paceSecondsPer500m   = paceSecondsPer500m,
                            strokesCount         = null
                        )
                        sessionsVm.addSession(session)
                        Log.d("video", "Session saved: $session")
                        Log.d("video", "Every session: ${sessionsVm.sessions.value}")
                        navController.navigate("sessions")
                    },
                    modifier = Modifier
                        .size(86.dp)
                        .padding(4.dp)   // inset the icon slightly
                ) {
                    Icon(
                        imageVector = Icons.Default.StopCircle,
                        contentDescription = "Stop",
                        tint = Color.Red,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
