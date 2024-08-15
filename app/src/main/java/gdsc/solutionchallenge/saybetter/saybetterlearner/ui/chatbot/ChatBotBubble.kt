package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainBlue
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

@Composable
fun ChatBubble(
    chatMessage: ChatMessage,
    isSelected : Boolean,
    highlightingIndex : Pair<Int,Int>,
    isSpeaking : Boolean,
    onClickReListen:() -> Unit
) {
    val messageArrangement = if (chatMessage.isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 20.dp, top = 10.dp),
        horizontalArrangement = messageArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (chatMessage.isUser) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(id = if(isSpeaking && isSelected) R.drawable.ic_speaker_on
                    else R.drawable.ic_speaker_off),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                if ((!isSpeaking && isSelected) || !isSelected) {
                    Text(
                        text = "다시 듣기",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {
                            onClickReListen()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            MessageBox(
                chatMessage,
                highlightingIndex,
                isSelected
            )
        } else {
            ProfileImage(
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(48.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            MessageBox(
                chatMessage,
                highlightingIndex,
                isSelected
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = if(isSpeaking && isSelected) R.drawable.ic_speaker_on
                    else R.drawable.ic_speaker_off),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                if ((!isSpeaking && isSelected) || !isSelected) {
                    Text(
                        text = "다시 듣기",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {
                            onClickReListen()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBox(
    chatMessage: ChatMessage,
    highlightingIndex : Pair<Int,Int>,
    isSelected : Boolean
) {
    val maxWidthDp = LocalConfiguration.current.screenWidthDp.dp * 3 / 6

    Box(
        modifier = Modifier
            .widthIn(max = if (chatMessage.isUser) maxWidthDp else maxWidthDp - 56.dp)
            .clip(
                if (chatMessage.isUser) {
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 0.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                } else {
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                }
            )
            .background(if (chatMessage.isUser) DarkGray else MainGreen)
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {

        if (isSelected) {
            val annotatedString = buildAnnotatedString {
                val text = chatMessage.message
                append(text)
                addStyle(
                    style = SpanStyle(background = MainBlue),
                    start = highlightingIndex.first,
                    end = highlightingIndex.second
                )
            }
            Text(
                text = annotatedString,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }else {
            Text(
                text = chatMessage.message,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }

    }
}