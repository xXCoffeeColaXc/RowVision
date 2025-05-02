package com.example.rowvision.app.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.R
import com.example.rowvision.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    vm: ProfileViewModel = hiltViewModel()
) {
    val name = vm.userName.collectAsState().value

    Scaffold(
        //containerColor = LightGray,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = AppTypography.headlineMedium,
                        color = DeepBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor             = White,
                    navigationIconContentColor = DeepBlue,
                    titleContentColor          = DeepBlue
                )
            )
        }

    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar + verified badge
                Box {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(DeepBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Avatar",
                            tint = White,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .background(AlertGreen, shape = CircleShape)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = name,
                    style = AppTypography.headlineSmall,
                    color = DeepBlue
                )

                Spacer(Modifier.height(24.dp))

                // Settings card
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.elevatedCardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // menu items
                        val items = listOf(
                            "Achievements"         to "achievements",
                            "Training preferences" to "training",
                            "Notifications"        to "notifications",
                            "Appearance"           to "appearance",
                            "Privacy & Security"   to "privacy",
                            "Help & Support"       to "help",
                            "Logout"               to "logout"
                        )

                        items.forEachIndexed { idx, (label, route) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (route == "logout") {
                                            navController.navigate("login") { popUpTo(0) }
                                        } else {
                                            navController.navigate(route)
                                        }
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text     = label,
                                    style    = AppTypography.bodyLarge,
                                    color    = DeepBlue,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint         = GreyShade
                                )
                            }
                            if (idx < items.lastIndex) {
                                Divider(color = GreyShade.copy(alpha = 0.4f))
                            }
                        }
                    }
                }
            }
        }
    }
}
