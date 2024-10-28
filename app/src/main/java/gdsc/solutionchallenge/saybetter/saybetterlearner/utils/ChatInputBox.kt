package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray5B
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.PaleGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.PretendardTypography

@Composable
fun ChatInputBox(
    chatState: String,
    onTextChange: (String) -> Unit,
    onChatSend: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .border(
                width = 2.dp, color = Gray500, shape = RoundedCornerShape(size = 30.dp)
            )
            .fillMaxWidth()
            .height(50.dp)
    ) {
        BasicTextField(
            value = chatState,
            onValueChange = { onTextChange(it) },
            textStyle = PretendardTypography.bodyMedium.copy(Color.White),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        
        Row (modifier = Modifier
            .clip(RoundedCornerShape(30.dp)) // 모서리를 둥글게 만듦
            .background(if (chatState.length > 0)PaleGreen
            else Gray400) // 배경색 설정
            .width(100.dp)
            .height(40.dp)
            .clickable {
                onChatSend(chatState)
                onTextChange("")
                       },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ){
            Text(text = "Enter",
                color = Black)
            
            Spacer(modifier = Modifier.width(5.dp))

            Image(painter = painterResource(id = R.drawable.vector),
                contentDescription = null,
                Modifier.size(18.dp));
        }
        Spacer(modifier = Modifier.width(4.dp))



    }
}