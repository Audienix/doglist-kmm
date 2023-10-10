package com.forbes.doglist.android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@Composable
fun DogListAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    DogListAppColorPalette(darkTheme, content)
}

@Composable
private fun DogListAppColorPalette(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorPalette = if (darkTheme) DarkColorPalette else LightColorPalette

    SetStatusBarColor(colorPalette.surfaceContainerLow, darkTheme)
    CompositionLocalProvider(
        LocalColorPalette provides colorPalette
    ) {
        MaterialTheme(
            content = content
        )
    }
}

@Composable
fun SetStatusBarColor(
    statusBarColor: Color,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
}

/**
 * The main background for the app.
 *
 * @param modifier Modifier to be applied to the background.
 * @param color Common App Background Color
 * @param content The background content.
 */
@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    color:Color = MaterialColorPalette.surface,
    content: @Composable () -> Unit
) {
    DogListAppTheme {
        Surface(
            color = color,
            modifier = modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
                content()
            }
        }
    }
}
