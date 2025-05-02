package com.example.rowvision.app.ui.analysis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.ui.theme.*
import com.example.rowvision.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    navController: NavController,
    vm: AnalysisViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Analysis",
                        style = AppTypography.headlineMedium,
                        color = DeepBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("sessions") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor            = White,
                    navigationIconContentColor= DeepBlue,
                    titleContentColor         = DeepBlue,
                    actionIconContentColor    = DeepBlue
                )
            )
        },
        // containerColor = LightGray
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // subtle wave background
            Image(
                painter = painterResource(R.drawable.bg_login_water),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.4f),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(DeepBlue.copy(alpha = 0.4f))
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card container
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = White)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Stroke-Phase Breakdown",
                            style = AppTypography.headlineSmall,
                            color = DeepBlue
                        )

                        Spacer(Modifier.height(24.dp))

                        // Donut chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        ) {
                            PieChart(
                                data             = vm.segments.map { it.second },
                                colors           = listOf(
                                    AlertGreen,
                                    UrgentOrange,
                                    AquaTurquoise,
                                    GreyShade,
                                    GreyShade,
                                    GreyShade
                                ),
                                centerText       = "${vm.cleanStrokes}\nclean strokes",
                                strokeWidth      = 24.dp,
                                centerTextStyle  = AppTypography.headlineMedium,
                                centerTextColor  = DeepBlue
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Text(
                            text = vm.duration,
                            style = AppTypography.headlineSmall,
                            color = DeepBlue
                        )

                        Spacer(Modifier.height(24.dp))

                        // Legend
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Correct zip + destructure
                            val colors = listOf(
                                AlertGreen,
                                UrgentOrange,
                                AquaTurquoise,
                                GreyShade,
                                GreyShade,
                                GreyShade
                            )
                            vm.segments.zip(colors).forEach { (segment, color) ->
                                val (label, pct) = segment
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        Modifier
                                            .size(12.dp)
                                            .background(color, shape = CircleShape)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text  = "${pct.toInt()}% $label",
                                        style = AppTypography.bodyLarge,
                                        color = DeepBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
