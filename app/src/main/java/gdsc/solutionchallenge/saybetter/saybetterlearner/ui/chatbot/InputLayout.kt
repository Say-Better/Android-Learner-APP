package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import HangulAutomaton
import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainBlue
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ChatInput(
    context : Context,
    onClickTransmit:(String)->Unit) {
    var inputMode by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    val hangul = remember { HangulAutomaton() }

    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) as Vibrator
    val vibrationEffect = VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE)

    val onCharacterClick: (Char) -> Unit = { char ->
        hangul.commit(char)
        inputText = hangul.content
        vibrator.vibrate(vibrationEffect)
    }

    val onBackClick: () -> Unit = {
        hangul.delete()
        inputText = hangul.content
        vibrator.vibrate(vibrationEffect)
    }

    val onSpaceClick: () -> Unit = {
        hangul.commitSpace()
        inputText = hangul.content
        vibrator.vibrate(vibrationEffect)
    }
    val SymbolClick: (String) -> Unit = { input->
        hangul.content += (" " + input)
        inputText += (" " + input)
        vibrator.vibrate(vibrationEffect)
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
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.83f)
                    .fillMaxHeight(0.15f)
                    .background(Gray200, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = inputText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .background(MainBlue, shape = RoundedCornerShape(50.dp))
                    .padding(10.dp)
                    .clickable {
                               onClickTransmit(inputText)
                        hangul.content = ""
                        inputText=""
                    },
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
        Row {
            if (!inputMode) InputSymbol(Modifier, SymbolClick)
            else InputKeyboard(
                modifier = Modifier,
                onCharacterClick = onCharacterClick,
                onBackClick = onBackClick,
                onSpaceClick = onSpaceClick
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id =
                    if (!inputMode) R.drawable.ic_symbol_active
                    else R.drawable.ic_symbol_inactive),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            inputMode = false
                        },
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    painter = painterResource(id =
                    if (inputMode) R.drawable.ic_keyboard_active
                    else R.drawable.ic_keyboard_inactive),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            inputMode = true
                        },
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
