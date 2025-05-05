package com.example.rowvision.app.ui.sessions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.R
import com.example.rowvision.app.data.model.Session
import com.example.rowvision.app.ui.theme.AquaTurquoise
import com.example.rowvision.app.ui.theme.AlertGreen
import com.example.rowvision.app.ui.theme.AppTypography
import com.example.rowvision.app.ui.theme.DeepBlue
import com.example.rowvision.app.ui.theme.GreyShade
import com.example.rowvision.app.ui.theme.LightGray
import com.example.rowvision.app.ui.theme.UrgentOrange
import com.example.rowvision.app.ui.theme.White
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionsScreen(
    navController: NavController,
    vm: SessionsViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sessions",
                        style = AppTypography.headlineMedium,
                        color = DeepBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
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
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // subtle wave in background
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                items(vm.sessions.value, key = { it.id }) { session ->
                    SessionItem(
                        session = session,
                        onClick = { navController.navigate("analysis") }
                    )
                }
            }
        }
    }
}

@Composable
private fun SessionItem(
    session: Session,
    onClick: () -> Unit
) {
    // Date/time formatting
    val dateFmt = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
    fun formatDuration(sec: Int): String {
        val m = sec / 60
        val s = sec % 60
        return if (s == 0) "$m min" else "$m min $s sec"
    }
    fun formatPace(sec500: Int): String {
        val m = sec500 / 60
        val s = sec500 % 60
        return String.format("%d:%02d /500m", m, s)
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = White
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            // Header: #id + colored chip + chevron
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "#${session.id}",
                    style = MaterialTheme.typography.labelLarge,
                    color = DeepBlue
                )

                AssistChip(
                    onClick = { /* optional filter */ },
                    label = { Text(session.type) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (session.type) {
                            "Interval" -> UrgentOrange.copy(alpha = 0.2f)
                            else       -> AquaTurquoise.copy(alpha = 0.2f)
                        },
                        labelColor = when (session.type) {
                            "Interval" -> UrgentOrange
                            else       -> AquaTurquoise
                        }
                    ),
                    modifier = Modifier.height(28.dp)
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = GreyShade
                )
            }

            Spacer(Modifier.height(12.dp))

            // Row 1: Date & Duration
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GreyShade
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    session.dateTime.format(dateFmt),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GreyShade
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    formatDuration(session.durationSeconds),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )
            }

            Spacer(Modifier.height(8.dp))

            // Row 2: Distance & Pace
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Terrain,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GreyShade
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "${session.distanceMeters} m",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GreyShade
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    formatPace(session.paceSecondsPer500m),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )
            }
        }
    }
}
