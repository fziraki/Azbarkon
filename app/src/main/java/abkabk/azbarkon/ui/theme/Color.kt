package abkabk.azbarkon.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val black = Color(0xFF000000)
private val white = Color(0xFFFFFFFF)

private val brown_603913 = Color(0xFF603913)
private val brown_8b7160 = Color(0xFF8b7160)
private val beige_c7b299 = Color(0xFFc7b299)
private val beige_alpha = Color(0xeec7b299)
private val beige_2alpha = Color(0xbcc7b299)
private val cream_efeddd = Color(0xFFefeddd)
private val off_white = Color(0xFFf5f4ea)
private val green_528b7e = Color(0xFF528b7e)

interface ColorVariant {
    val light: Color
    val dark: Color
}

enum class DefaultColorsVariant : ColorVariant {

    Background {
        override val light: Color = beige_c7b299
        override val dark: Color = brown_8b7160
    },

    OnSurface {
        override val light: Color = black
        override val dark: Color = white
    },

    PoetBg {
        override val light: Color = off_white
        override val dark: Color = beige_c7b299
    },

    PinTint {
        override val light: Color = brown_8b7160
        override val dark: Color = brown_8b7160
    },

    SelectedNav {
        override val light: Color = off_white
        override val dark: Color = cream_efeddd
    },

    UnSelectedNav {
        override val light: Color = brown_8b7160
        override val dark: Color = brown_8b7160
    },

    SelectedPoet {
        override val light: Color = beige_2alpha
        override val dark: Color = beige_2alpha
    }

}

@Immutable
data class Colors(
    val background: Color,
    val onSurface: Color,
    val poetBg: Color,
    val pinTint: Color,
    val selectedNav: Color,
    val unSelectedNav: Color,
    val selectedPoet: Color,
    val isLight: Boolean
)

object DefaultColors {
    val lightColors: Colors = Colors(
        background = DefaultColorsVariant.Background.light,
        onSurface = DefaultColorsVariant.OnSurface.light,
        poetBg = DefaultColorsVariant.PoetBg.light,
        pinTint = DefaultColorsVariant.PinTint.light,
        selectedNav = DefaultColorsVariant.SelectedNav.light,
        unSelectedNav = DefaultColorsVariant.UnSelectedNav.light,
        selectedPoet = DefaultColorsVariant.SelectedPoet.light,
        isLight = true
    )

    val darkColors: Colors = Colors(
        background = DefaultColorsVariant.Background.dark,
        onSurface = DefaultColorsVariant.OnSurface.dark,
        poetBg = DefaultColorsVariant.PoetBg.dark,
        pinTint = DefaultColorsVariant.PinTint.dark,
        selectedNav = DefaultColorsVariant.SelectedNav.dark,
        unSelectedNav = DefaultColorsVariant.UnSelectedNav.dark,
        selectedPoet = DefaultColorsVariant.SelectedPoet.dark,
        isLight = false
    )
}


val LocalColor = staticCompositionLocalOf {
    DefaultColors.lightColors
}