package uy.com.temperoni.recipes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppDarkColorScheme = darkColorScheme(
        primary = Purple200,
        secondary = Purple700,
        tertiary = Teal200,
        onSurface = Color.White,
        surface = DarkGray,
        onSecondary = Gray
)

private val AppLightColorScheme = lightColorScheme(
        primary = Purple500,
        secondary = Purple700,
        tertiary = Teal200,
        onSurface = Color.Black
)

@Composable
fun RecetasTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        AppDarkColorScheme
    } else {
        AppLightColorScheme
    }

    MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes(),
            content = content
    )
}