package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import HangulAutomaton
import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainBlue
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Transparent
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.clickWithScaleAnimation

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ChatInput(
    context : Context,
    onClickTransmit:(String)->Unit) {
    var inputMode by remember { mutableStateOf(true) }
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val hangul = remember { HangulAutomaton() }

    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) as Vibrator
    val vibrationEffect = VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE)

    val SymbolClick: (String) -> Unit = { input->
        inputText = TextFieldValue(
            text = inputText.text + (" " + input + " "),
            selection = TextRange(inputText.text.length + input.length + 2) // 커서를 텍스트의 끝으로 설정
        )
    }

    Canvas(
        modifier = Modifier.fillMaxWidth()
    ) {
        drawLine(
            color = Gray200,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 3f,
        )
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
                    .height(60.dp)
                    .background(Gray200, shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // 부모 Row의 높이에 맞추기
                        .padding(horizontal = 10.dp), // 좌우에만 패딩 설정
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Transparent, // 배경색 투명
                        focusedIndicatorColor = Transparent, // 포커스 시 테두리색 투명
                        unfocusedIndicatorColor = Transparent, // 비포커스 시 테두리색 투명
                        disabledIndicatorColor = Transparent, // 비활성화 시 테두리색 투명
                        textColor = Color.Black, // 텍스트 색상 설정
                    )
                )
                Image(
                    painter = painterResource(id =
                    if (!inputMode) R.drawable.ic_symbol_active
                    else R.drawable.ic_symbol_inactive),
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .padding(5.dp)
                        .clickWithScaleAnimation({inputMode = !inputMode}),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MainBlue, shape = RoundedCornerShape(50.dp))
                    .clickWithScaleAnimation({
                        onClickTransmit(inputText.text)
                        hangul.content = ""
                        inputText = TextFieldValue("")
                    }),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_transport),
                        contentDescription = null
                    )
                    Text(
                        text = "전송",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(
            visible = !inputMode, // Animation will trigger based on inputMode
            enter = fadeIn() + expandVertically(), // Animation effects when appearing
            exit = fadeOut() + shrinkVertically() // Animation effects when disappearing
        ) {
            InputSymbol(Modifier, SymbolClick)
        }
    }
}
