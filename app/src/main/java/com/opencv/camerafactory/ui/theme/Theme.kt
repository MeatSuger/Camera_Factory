package com.example.compose

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.ui.theme.AppTypography
import com.opencv.camerafactory.ui.theme.backgroundDark
import com.opencv.camerafactory.ui.theme.backgroundDarkHighContrast
import com.opencv.camerafactory.ui.theme.backgroundDarkMediumContrast
import com.opencv.camerafactory.ui.theme.backgroundLight
import com.opencv.camerafactory.ui.theme.backgroundLightHighContrast
import com.opencv.camerafactory.ui.theme.backgroundLightMediumContrast
import com.opencv.camerafactory.ui.theme.errorContainerDark
import com.opencv.camerafactory.ui.theme.errorContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.errorContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.errorContainerLight
import com.opencv.camerafactory.ui.theme.errorContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.errorContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.errorDark
import com.opencv.camerafactory.ui.theme.errorDarkHighContrast
import com.opencv.camerafactory.ui.theme.errorDarkMediumContrast
import com.opencv.camerafactory.ui.theme.errorLight
import com.opencv.camerafactory.ui.theme.errorLightHighContrast
import com.opencv.camerafactory.ui.theme.errorLightMediumContrast
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceDark
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceDarkHighContrast
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceLight
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceLightHighContrast
import com.opencv.camerafactory.ui.theme.inverseOnSurfaceLightMediumContrast
import com.opencv.camerafactory.ui.theme.inversePrimaryDark
import com.opencv.camerafactory.ui.theme.inversePrimaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.inversePrimaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.inversePrimaryLight
import com.opencv.camerafactory.ui.theme.inversePrimaryLightHighContrast
import com.opencv.camerafactory.ui.theme.inversePrimaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.inverseSurfaceDark
import com.opencv.camerafactory.ui.theme.inverseSurfaceDarkHighContrast
import com.opencv.camerafactory.ui.theme.inverseSurfaceDarkMediumContrast
import com.opencv.camerafactory.ui.theme.inverseSurfaceLight
import com.opencv.camerafactory.ui.theme.inverseSurfaceLightHighContrast
import com.opencv.camerafactory.ui.theme.inverseSurfaceLightMediumContrast
import com.opencv.camerafactory.ui.theme.onBackgroundDark
import com.opencv.camerafactory.ui.theme.onBackgroundDarkHighContrast
import com.opencv.camerafactory.ui.theme.onBackgroundDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onBackgroundLight
import com.opencv.camerafactory.ui.theme.onBackgroundLightHighContrast
import com.opencv.camerafactory.ui.theme.onBackgroundLightMediumContrast
import com.opencv.camerafactory.ui.theme.onErrorContainerDark
import com.opencv.camerafactory.ui.theme.onErrorContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.onErrorContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onErrorContainerLight
import com.opencv.camerafactory.ui.theme.onErrorContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.onErrorContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.onErrorDark
import com.opencv.camerafactory.ui.theme.onErrorDarkHighContrast
import com.opencv.camerafactory.ui.theme.onErrorDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onErrorLight
import com.opencv.camerafactory.ui.theme.onErrorLightHighContrast
import com.opencv.camerafactory.ui.theme.onErrorLightMediumContrast
import com.opencv.camerafactory.ui.theme.onPrimaryContainerDark
import com.opencv.camerafactory.ui.theme.onPrimaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.onPrimaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onPrimaryContainerLight
import com.opencv.camerafactory.ui.theme.onPrimaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.onPrimaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.onPrimaryDark
import com.opencv.camerafactory.ui.theme.onPrimaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.onPrimaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onPrimaryLight
import com.opencv.camerafactory.ui.theme.onPrimaryLightHighContrast
import com.opencv.camerafactory.ui.theme.onPrimaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.onSecondaryContainerDark
import com.opencv.camerafactory.ui.theme.onSecondaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.onSecondaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onSecondaryContainerLight
import com.opencv.camerafactory.ui.theme.onSecondaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.onSecondaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.onSecondaryDark
import com.opencv.camerafactory.ui.theme.onSecondaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.onSecondaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onSecondaryLight
import com.opencv.camerafactory.ui.theme.onSecondaryLightHighContrast
import com.opencv.camerafactory.ui.theme.onSecondaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.onSurfaceDark
import com.opencv.camerafactory.ui.theme.onSurfaceDarkHighContrast
import com.opencv.camerafactory.ui.theme.onSurfaceDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onSurfaceLight
import com.opencv.camerafactory.ui.theme.onSurfaceLightHighContrast
import com.opencv.camerafactory.ui.theme.onSurfaceLightMediumContrast
import com.opencv.camerafactory.ui.theme.onSurfaceVariantDark
import com.opencv.camerafactory.ui.theme.onSurfaceVariantDarkHighContrast
import com.opencv.camerafactory.ui.theme.onSurfaceVariantDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onSurfaceVariantLight
import com.opencv.camerafactory.ui.theme.onSurfaceVariantLightHighContrast
import com.opencv.camerafactory.ui.theme.onSurfaceVariantLightMediumContrast
import com.opencv.camerafactory.ui.theme.onTertiaryContainerDark
import com.opencv.camerafactory.ui.theme.onTertiaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.onTertiaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onTertiaryContainerLight
import com.opencv.camerafactory.ui.theme.onTertiaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.onTertiaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.onTertiaryDark
import com.opencv.camerafactory.ui.theme.onTertiaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.onTertiaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.onTertiaryLight
import com.opencv.camerafactory.ui.theme.onTertiaryLightHighContrast
import com.opencv.camerafactory.ui.theme.onTertiaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.outlineDark
import com.opencv.camerafactory.ui.theme.outlineDarkHighContrast
import com.opencv.camerafactory.ui.theme.outlineDarkMediumContrast
import com.opencv.camerafactory.ui.theme.outlineLight
import com.opencv.camerafactory.ui.theme.outlineLightHighContrast
import com.opencv.camerafactory.ui.theme.outlineLightMediumContrast
import com.opencv.camerafactory.ui.theme.outlineVariantDark
import com.opencv.camerafactory.ui.theme.outlineVariantDarkHighContrast
import com.opencv.camerafactory.ui.theme.outlineVariantDarkMediumContrast
import com.opencv.camerafactory.ui.theme.outlineVariantLight
import com.opencv.camerafactory.ui.theme.outlineVariantLightHighContrast
import com.opencv.camerafactory.ui.theme.outlineVariantLightMediumContrast
import com.opencv.camerafactory.ui.theme.primaryContainerDark
import com.opencv.camerafactory.ui.theme.primaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.primaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.primaryContainerLight
import com.opencv.camerafactory.ui.theme.primaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.primaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.primaryDark
import com.opencv.camerafactory.ui.theme.primaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.primaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.primaryLight
import com.opencv.camerafactory.ui.theme.primaryLightHighContrast
import com.opencv.camerafactory.ui.theme.primaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.scrimDark
import com.opencv.camerafactory.ui.theme.scrimDarkHighContrast
import com.opencv.camerafactory.ui.theme.scrimDarkMediumContrast
import com.opencv.camerafactory.ui.theme.scrimLight
import com.opencv.camerafactory.ui.theme.scrimLightHighContrast
import com.opencv.camerafactory.ui.theme.scrimLightMediumContrast
import com.opencv.camerafactory.ui.theme.secondaryContainerDark
import com.opencv.camerafactory.ui.theme.secondaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.secondaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.secondaryContainerLight
import com.opencv.camerafactory.ui.theme.secondaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.secondaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.secondaryDark
import com.opencv.camerafactory.ui.theme.secondaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.secondaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.secondaryLight
import com.opencv.camerafactory.ui.theme.secondaryLightHighContrast
import com.opencv.camerafactory.ui.theme.secondaryLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceBrightDark
import com.opencv.camerafactory.ui.theme.surfaceBrightDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceBrightDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceBrightLight
import com.opencv.camerafactory.ui.theme.surfaceBrightLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceBrightLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerDark
import com.opencv.camerafactory.ui.theme.surfaceContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighDark
import com.opencv.camerafactory.ui.theme.surfaceContainerHighDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighLight
import com.opencv.camerafactory.ui.theme.surfaceContainerHighLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestDark
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestLight
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerHighestLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLight
import com.opencv.camerafactory.ui.theme.surfaceContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowDark
import com.opencv.camerafactory.ui.theme.surfaceContainerLowDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowLight
import com.opencv.camerafactory.ui.theme.surfaceContainerLowLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestDark
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestLight
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceContainerLowestLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceDark
import com.opencv.camerafactory.ui.theme.surfaceDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceDimDark
import com.opencv.camerafactory.ui.theme.surfaceDimDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceDimDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceDimLight
import com.opencv.camerafactory.ui.theme.surfaceDimLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceDimLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceLight
import com.opencv.camerafactory.ui.theme.surfaceLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceLightMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceVariantDark
import com.opencv.camerafactory.ui.theme.surfaceVariantDarkHighContrast
import com.opencv.camerafactory.ui.theme.surfaceVariantDarkMediumContrast
import com.opencv.camerafactory.ui.theme.surfaceVariantLight
import com.opencv.camerafactory.ui.theme.surfaceVariantLightHighContrast
import com.opencv.camerafactory.ui.theme.surfaceVariantLightMediumContrast
import com.opencv.camerafactory.ui.theme.tertiaryContainerDark
import com.opencv.camerafactory.ui.theme.tertiaryContainerDarkHighContrast
import com.opencv.camerafactory.ui.theme.tertiaryContainerDarkMediumContrast
import com.opencv.camerafactory.ui.theme.tertiaryContainerLight
import com.opencv.camerafactory.ui.theme.tertiaryContainerLightHighContrast
import com.opencv.camerafactory.ui.theme.tertiaryContainerLightMediumContrast
import com.opencv.camerafactory.ui.theme.tertiaryDark
import com.opencv.camerafactory.ui.theme.tertiaryDarkHighContrast
import com.opencv.camerafactory.ui.theme.tertiaryDarkMediumContrast
import com.opencv.camerafactory.ui.theme.tertiaryLight
import com.opencv.camerafactory.ui.theme.tertiaryLightHighContrast
import com.opencv.camerafactory.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun CameraFactoryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

