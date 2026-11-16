package com.example.marketgo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = OnTerracotta,
    primaryContainer = PeachContainer,
    onPrimaryContainer = OnPeachContainer,
    secondary = SageSecondary,
    secondaryContainer = SageContainer,
    background = CreamBackground,
    onBackground = CocoaText,
    surface = CreamBackground,
    onSurface = CocoaText,
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = TerracottaPrimaryDark,
    onPrimary = OnTerracottaDark,
    primaryContainer = PeachContainerDark,
    onPrimaryContainer = OnPeachContainerDark,
    secondary = SageSecondaryDark,
    secondaryContainer = SageContainerDark,
    background = CocoaBackgroundDark,
    onBackground = CreamTextDark,
    surface = CocoaBackgroundDark,
    onSurface = CreamTextDark
)

@Composable
fun MarketGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}