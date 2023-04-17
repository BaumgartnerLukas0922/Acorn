package at.htl.acornapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = apricot,
    primaryVariant = dark_blue_gray,
    onPrimary = dark_blue_gray,
    secondary = riper_apricot,
    secondaryVariant = riper_apricot,
    onSecondary = light_gray
)

private val LightColorPalette = lightColors(
    primary = dark_blue_gray,
    primaryVariant = dark_blue_gray,
    onPrimary = light_gray,
    secondary = apricot,
    secondaryVariant = riper_apricot,
    onSecondary = riper_apricot
)

@Composable
fun AcornTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}