package abkabk.azbarkon.ui.theme

import abkabk.azbarkon.R
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object DefaultFontFamily {
    val regular = FontFamily(Font(R.font.samimfd, FontWeight.Normal))
}

@Immutable
data class Typography(
    val title1: TextStyle,
    val subtitle1: TextStyle
)


object DefaultTypography {

    val typography: Typography = Typography(
        title1 = TextStyle(
            fontFamily = DefaultFontFamily.regular,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = DefaultFontFamily.regular,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp
        )
    )
}

val LocalTypography = staticCompositionLocalOf {
    DefaultTypography.typography
}