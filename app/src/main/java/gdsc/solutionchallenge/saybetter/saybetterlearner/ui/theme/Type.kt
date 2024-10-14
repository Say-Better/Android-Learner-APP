package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R

interface BaseTypography {
    val headlineLarge: TextStyle
    val headlineMedium: TextStyle
    val buttonLarge: TextStyle
    val buttonMedium: TextStyle
    val buttonSmall: TextStyle
    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle
    val caption: TextStyle
}

val pretendardBoldFont = Font(R.font.pretendard_bold)
val pretendardMediumFont = Font(R.font.pretendard_medium)
val pretendardRegularFont = Font(R.font.pretendard_regular)

val PretendardTypography : BaseTypography = object : BaseTypography {
    override val headlineLarge = TextStyle(
        fontFamily = FontFamily(pretendardBoldFont),
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    )
    override val headlineMedium = TextStyle(
        fontFamily = FontFamily(pretendardMediumFont),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
    )
    override val buttonLarge = TextStyle(
        fontFamily = FontFamily(pretendardBoldFont),
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
    )
    override val buttonMedium = TextStyle(
        fontFamily = FontFamily(pretendardMediumFont),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
    )
    override val buttonSmall = TextStyle(
        fontFamily = FontFamily(pretendardMediumFont),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
    )
    override val bodyLarge = TextStyle(
        fontFamily = FontFamily(pretendardRegularFont),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
    )
    override val bodyMedium = TextStyle(
        fontFamily = FontFamily(pretendardRegularFont),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
    )
    override val bodySmall = TextStyle(
        fontFamily = FontFamily(pretendardMediumFont),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
    )
    override val caption = TextStyle(
        fontFamily = FontFamily(pretendardRegularFont),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.6.sp,
    )
}