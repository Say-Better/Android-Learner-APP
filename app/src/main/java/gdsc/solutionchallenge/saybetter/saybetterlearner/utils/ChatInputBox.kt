package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray5B
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.PretendardTypography

@Composable
fun ChatInputBox(
    chatState: String,
    onTextChange: (String) -> Unit,
    onChatSend: (String) -> Unit
) {
    Text(
        text = "인사말을 입력해 보세요",
        style = PretendardTypography.bodySmall.copy(Color.White),
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 12.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .border(
                width = 1.dp, color = Color.White, shape = RoundedCornerShape(size = 12.dp)
            )
            .fillMaxWidth(0.5f)
            .height(60.dp)
    ) {
        BasicTextField(
            value = chatState,
            onValueChange = { onTextChange(it) },
            textStyle = PretendardTypography.bodyMedium.copy(Color.White),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
        )

        Image(painter = painterResource(id = R.drawable.send),
            contentDescription = "send button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically)
                .clickable { onChatSend(chatState) }
        )
    }
}