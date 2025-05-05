package com.example.rowvision.app

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rowvision.app.ui.analysis.AnalysisScreen
import com.example.rowvision.app.ui.home.HomeScreen
import com.example.rowvision.app.ui.login.LoginScreen
import com.example.rowvision.app.ui.prestart.PreStartScreen
import com.example.rowvision.app.ui.profile.ProfileScreen
import com.example.rowvision.app.ui.sessions.SessionsScreen
import com.example.rowvision.app.ui.theme.RowVisionTheme
import com.example.rowvision.app.ui.video.VideoScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RowVisionTheme {
                RowVisionApp()
            }
        }
    }
}

@Composable
fun RowVisionApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("login")    { LoginScreen(navController) }
        composable("home")     { HomeScreen(navController) }
        composable("prestart") { PreStartScreen(navController) }
        composable("video")    { VideoScreen(navController)    }
        composable("sessions") { SessionsScreen(navController) }
        composable("analysis") { AnalysisScreen(navController) }
        composable("profile")  { ProfileScreen(navController) }

    }
}