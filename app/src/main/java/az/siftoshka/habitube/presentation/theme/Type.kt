package az.siftoshka.habitube.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import az.siftoshka.habitube.R

val fontFamily = FontFamily(
        Font(R.font.rubik_light, FontWeight.Light),
        Font(R.font.rubik_regular, FontWeight.Normal),
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_semibold, FontWeight.SemiBold),
        Font(R.font.rubik_bold, FontWeight.Bold),
)

val Typography = Typography(
        h1 = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = fontFamily
        ),
        h2 = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                fontFamily = fontFamily
        ),
        h3 = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                fontFamily = fontFamily
        ),
        h4 = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontFamily = fontFamily
        ),
        h5 = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = fontFamily
        ),
        h6 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = fontFamily
        ),
        body1 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                fontFamily = fontFamily
        ),
        body2 = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                fontFamily = fontFamily
        )
)