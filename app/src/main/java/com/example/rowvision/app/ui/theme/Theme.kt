package com.example.rowvision.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

import androidx.compose.material3.Shapes
import com.example.rowvision.app.ui.theme.AppTypography

private val LightColorScheme = lightColorScheme(
    primary        = DeepBlue,
    secondary      = AquaTurquoise,
    background     = LightGray,
    surface        = White,
    error          = UrgentOrange,
    onPrimary      = White,
    onSecondary    = DeepBlue,
    onBackground   = DeepBlue,
    onSurface      = DeepBlue,
    onError        = White
)

@Composable
fun RowVisionTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = AppTypography,  // use default or customize as needed
        shapes      = Shapes(),      // default shapes
        content     = content
    )
}