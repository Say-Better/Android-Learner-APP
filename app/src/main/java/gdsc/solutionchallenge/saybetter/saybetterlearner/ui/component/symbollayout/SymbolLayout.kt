package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.symbollayout

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.HighlightBorder
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent


@Composable
fun Symbol(
    modifier: Modifier,
    isSelected: Boolean,
    onSymbolClick: () -> Unit,
    context: Context
) {
    val borderColor = if (isSelected) HighlightBorder else SubGrey
    val textColor = if (isSelected) HighlightBorder else White

    BoxWithConstraints(
        modifier = modifier
            .background(DarkGray, RoundedCornerShape(10.dp))
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                onSymbolClick()
            },
        contentAlignment = Alignment.Center
    ) {
        val boxSize = maxHeight * 0.9f // 최대 너비를 기준으로 크기 조정
        val fontSize = boxSize.value * 0.1f / 2 // 박스 크기의 10%로 폰트 크기 조정

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.symbol_big),
                contentDescription = null,
                modifier = Modifier
                    .size(boxSize)
                    .padding(15.dp), // 박스 크기의 50%로 이미지 크기 조정
                contentScale = ContentScale.FillBounds
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "~로 가요",
                    fontSize = fontSize.sp,
                    color = textColor,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}


@Composable
fun ReadySymbol(modifier: Modifier) {
    val isClicked = remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .background(DarkGray, RoundedCornerShape(10.dp))
            .border(1.dp, SubGrey, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        val boxSize = maxHeight *0.9f // 최대 너비를 기준으로 크기 조정
        val fontSize = boxSize.value * 0.1f / 2 // 박스 크기의 10%로 폰트 크기 조정

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_ready_symbol),
                contentDescription = null,
                modifier = Modifier.size(boxSize)
                    .padding(15.dp), // 박스 크기의 50%로 이미지 크기 조정
                contentScale = ContentScale.FillBounds
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "-",
                    fontSize = fontSize.sp,
                    color = White,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}