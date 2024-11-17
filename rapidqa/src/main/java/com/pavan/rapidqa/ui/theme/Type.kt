package com.pavan.rapidqa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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

internal fun TextStyle.bold(): TextStyle {
    return copy(fontWeight = FontWeight.Bold)
}

internal fun TextStyle.regular(): TextStyle {
    return copy(fontWeight = FontWeight.Normal)
}

internal fun TextStyle.light(): TextStyle {
    return copy(fontWeight = FontWeight.Light)
}

internal fun TextStyle.italic(): TextStyle {
    return copy(fontStyle = FontStyle.Italic)
}

internal fun TextStyle.underline(): TextStyle {
    return copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline)
}

