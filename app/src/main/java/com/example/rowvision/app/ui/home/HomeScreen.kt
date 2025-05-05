package com.example.rowvision.app.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.ui.theme.AquaTurquoise
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rowvision.app.R
import com.example.rowvision.app.ui.theme.AppTypography
import com.example.rowvision.app.ui.theme.DeepBlue
import com.example.rowvision.app.ui.theme.GreyShade
import com.example.rowvision.app.ui.theme.LightGray
import com.example.rowvision.app.ui.theme.UrgentOrange
import com.example.rowvision.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var showSettings by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // 1) Energetic background + dark overlay
        Image(
            painter = painterResource(R.drawable.bg_login_water),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
                //.alpha(0.4f),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        // start fully transparent at 20% down, then ramp up to DeepBlue@40%
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            DeepBlue.copy(alpha = 0.9f)
                        ),
                        startY = 0f,
                        endY   = Float.POSITIVE_INFINITY
                    )
                )
        )

        // 2) Scaffold sits on top, transparent so BG shows through
        Scaffold(containerColor = Color.Transparent) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement   = Arrangement.Center,
                horizontalAlignment   = Alignment.CenterHorizontally
            ) {
                // 1) Pulse + shadow Start button
                val pulse by rememberInfiniteTransition().animateFloat(
                    initialValue  = 1f,
                    targetValue   = 1.05f,
                    animationSpec = infiniteRepeatable(
                        animation  = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                Button(
                    onClick    = { navController.navigate("prestart") },
                    shape      = CircleShape,
                    modifier   = Modifier
                        .size(180.dp)
                        .graphicsLayer { scaleX = pulse; scaleY = pulse }
                        // stronger drop shadow
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape),
                    colors     = ButtonDefaults.buttonColors(containerColor = AquaTurquoise),
                    elevation  = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
                ) {
                    Text(
                        text = "START",
                        style = AppTypography.titleLarge.copy(
                            fontWeight    = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            // add a subtle text shadow
                            shadow = Shadow(
                                color      = Color(0x66000000),
                                offset     = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))

                // 2) Tighter icon row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()                               // take full width
                        .offset(y = (-22).dp)                          // lift the row up 24dp under the circle
                        .wrapContentWidth(Alignment.CenterHorizontally), // then center its contents
                    horizontalArrangement = Arrangement.spacedBy(90.dp) // space icons 80.dp apart
                ) {
                    IconButton(
                        onClick = { showSettings = true },
                        modifier = Modifier
                            .size(48.dp)
                            .background(AquaTurquoise.copy(alpha = 0.25f), CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                    IconButton(
                        onClick = { navController.navigate("sessions") },
                        modifier = Modifier
                            .size(48.dp)
                            .background(AquaTurquoise.copy(alpha = 0.25f), CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(Icons.Default.List, contentDescription = "Sessions", tint = Color.White)
                    }
                }
            }
        }

        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                sheetState       = sheetState,
                // white sheet instead of translucent navy
                containerColor   = White,
                // all its text/icons will be DeepBlue by default
                contentColor     = DeepBlue,
                tonalElevation   = 8.dp,       // give it some lift
                shape            = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            ) {
                SettingsSheet { showSettings = false }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsSheet(onDone: () -> Unit) {

    val homeVm: HomeViewModel = hiltViewModel()

    val current by homeVm.settings.collectAsState()
    var selectedWorkout  by remember { mutableStateOf(current.plan) }
    var selectedInterval by remember { mutableStateOf(current.interval) }
    var selectedGoal     by remember { mutableStateOf(current.goal) }

    // Sample options
    val workoutOptions  = listOf("Endurance", "Strength", "Interval")
    val intervalOptions = listOf("30s/30s", "1min/1min", "2min/1min")
    val goalOptions     = listOf("500m", "1km", "5km")

    // State for each dropdown
    var workoutExpanded by remember { mutableStateOf(false) }
    // var selectedWorkout by remember { mutableStateOf(workoutOptions[0]) }

    var intervalExpanded by remember { mutableStateOf(false) }
    // var selectedInterval by remember { mutableStateOf(intervalOptions[0]) }

    var goalExpanded by remember { mutableStateOf(false) }
    // var selectedGoal by remember { mutableStateOf(goalOptions[0]) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Session Settings",
            style = AppTypography.headlineMedium,
            color = DeepBlue
        )

        // Reuse this for each dropdown:
        val textFieldColors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedTextColor        = DeepBlue,
            unfocusedTextColor      = DeepBlue.copy(alpha = 0.7f),
            focusedContainerColor   = White,
            unfocusedContainerColor = White,
            disabledContainerColor  = LightGray,
            focusedIndicatorColor   = AquaTurquoise,
            unfocusedIndicatorColor = GreyShade
        )

        // Workout Plan
        ExposedDropdownMenuBox(
            expanded = workoutExpanded,
            onExpandedChange = { workoutExpanded = !workoutExpanded }
        ) {
            TextField(
                value = selectedWorkout,
                onValueChange = {},
                readOnly = true,
                label = { Text("Workout Plan") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = workoutExpanded,
                onDismissRequest = { workoutExpanded = false }
            ) {
                workoutOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedWorkout = option
                            workoutExpanded = false
                        }
                    )
                }
            }
        }

        // 3) Intervals (same pattern)
        ExposedDropdownMenuBox(
            expanded = intervalExpanded,
            onExpandedChange = { intervalExpanded = !intervalExpanded }
        ) {
            TextField(
                value = selectedInterval,
                onValueChange = {},
                readOnly = true,
                label = { Text("Intervals") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = intervalExpanded,
                onDismissRequest = { intervalExpanded = false }
            ) {
                intervalOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedInterval = option
                            intervalExpanded = false
                        }
                    )
                }
            }
        }

        // 4) Goal (same)
        ExposedDropdownMenuBox(
            expanded = goalExpanded,
            onExpandedChange = { goalExpanded = !goalExpanded }
        ) {
            TextField(
                value = selectedGoal,
                onValueChange = {},
                readOnly = true,
                label = { Text("Set a Goal") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = goalExpanded,
                onDismissRequest = { goalExpanded = false }
            ) {
                goalOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedGoal = option
                            goalExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // 5) Done button
        Button(
            onClick = {
                homeVm.saveSettings(selectedWorkout, selectedInterval, selectedGoal)
                Log.d("video", "Settings saved: ${homeVm.settings.value.plan}")
                Log.d("video", "Settings saved: ${homeVm.settings.value.interval}")
                Log.d("video", "Settings saved: ${homeVm.settings.value.goal}")
                onDone()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AquaTurquoise)
        ) {
            Text("Done", color = White)
        }
    }
}
