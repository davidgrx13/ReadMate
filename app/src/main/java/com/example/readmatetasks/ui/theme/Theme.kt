package com.example.readmatetasks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Esquema de colores para modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = AccentColor, // Se mantiene el color principal
    secondary = LightGray,
    tertiary = MainTitleColor,
    background = BackgroundColor, // Fondo oscuro realzado
    surface = BackgroundColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = BackgroundColor, // Texto sobre fondo oscuro
    onSurface = BackgroundColor
)

// Esquema de colores para modo claro
private val LightColorScheme = lightColorScheme(
    primary = AccentColor,
    secondary = LightGray,
    tertiary = MainTitleColor,
    background = BackgroundColor, // Fondo personalizado
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = BackgroundColor, // Texto sobre fondo claro
    onSurface = BackgroundColor
)

@Composable
fun ReadMateTasksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Opcional: Dynamic Colors en Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
