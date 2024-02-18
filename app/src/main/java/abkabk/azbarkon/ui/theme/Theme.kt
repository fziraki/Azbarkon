package abkabk.azbarkon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AzbarkonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DefaultColors.darkColors else DefaultColors.lightColors

    CompositionLocalProvider(
        LocalTypography provides DefaultTypography.typography,
        LocalColor provides colors,
        content = content
    )
}

object AzbarkonTheme {
    val typography: Typography
        @Composable
        @ReadOnlyComposable get() = LocalTypography.current

    val colors: Colors
        @Composable
        @ReadOnlyComposable get() = LocalColor.current
}