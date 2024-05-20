package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R

val pretendardMediumFont = Font(R.font.pretendard_medium)

val bodyLarge = TextStyle(
    fontFamily = FontFamily(pretendardMediumFont),
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)