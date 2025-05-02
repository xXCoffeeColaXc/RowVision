package com.example.rowvision.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.rowvision.app.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val MontserratFamily = FontFamily(
    Font(R.font.montserrat_regular,    weight = FontWeight.Normal),
    Font(R.font.montserrat_medium,     weight = FontWeight.Medium),
    Font(R.font.montserrat_semibold,   weight = FontWeight.SemiBold),
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 32.sp,
        lineHeight  = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight  = FontWeight.Medium,
        fontSize    = 24.sp,
        lineHeight  = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp,
        lineHeight  = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight  = FontWeight.Medium,
        fontSize    = 14.sp,
        lineHeight  = 20.sp
    )
)

