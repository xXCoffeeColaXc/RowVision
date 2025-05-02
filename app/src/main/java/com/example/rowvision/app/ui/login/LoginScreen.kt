package com.example.rowvision.app.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rowvision.app.R
import com.example.rowvision.app.ui.theme.AppTypography
import com.example.rowvision.app.ui.theme.AquaTurquoise
import com.example.rowvision.app.ui.theme.DeepBlue
import com.example.rowvision.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email     by viewModel.email.collectAsState()
    val password  by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error     by viewModel.error.collectAsState()

    Box(Modifier.fillMaxSize()) {
        // 1) Background + overlay
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

        // 2) Form
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App title / logo
            Image(
                painter = painterResource(R.drawable.rowvision_logo),
                contentDescription = "RowVision Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)        // adjust height as needed
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(32.dp))

            // Shared text‐field colors
            val outlinedColors = outlinedTextFieldColors(
                focusedTextColor        = White,
                unfocusedTextColor      = White.copy(alpha = 0.7f),
                cursorColor             = White.copy(alpha = 0.7f),
                focusedBorderColor      = AquaTurquoise,
                unfocusedBorderColor    = White.copy(alpha = 0.7f),
                focusedLabelColor       = White,
                unfocusedLabelColor     = White.copy(alpha = 0.7f)
            )

            // Email
            OutlinedTextField(
                value               = email,
                onValueChange       = viewModel::onEmailChange,
                label               = { Text("Email Address", fontStyle = Italic) },
                singleLine          = true,
                modifier            = Modifier.fillMaxWidth(),
                colors              = outlinedColors,
                keyboardOptions     = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(16.dp))

            // Password with show/hide toggle
            var showPassword by remember { mutableStateOf(false) }
            OutlinedTextField(
                value               = password,
                onValueChange       = viewModel::onPasswordChange,
                label               = { Text("Password", fontStyle = Italic) },
                singleLine          = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon        = {
                    val icon = if (showPassword)
                        Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { showPassword = !showPassword } ){
                        Icon(icon, contentDescription = null, tint = White)
                    }
                },
                modifier            = Modifier.fillMaxWidth(),
                colors              = outlinedColors,
                keyboardOptions     = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            // Login button
            Button(
                onClick      = { viewModel.login { navController.navigate("home") } },
                enabled      = !isLoading,
                modifier     = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors       = ButtonDefaults.buttonColors(containerColor = AquaTurquoise)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Login", color = White, style = AppTypography.bodyLarge, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Forgot / Register row
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { /* TODO */ }) {
                    Text("Forgot password?", color = White.copy(alpha = 0.9f))
                }
                TextButton(onClick = { /* TODO */ }) {
                    Text("Register now", color = AquaTurquoise)
                }
            }
        }
    }
}
