package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.clickWithScaleAnimation
import kotlinx.coroutines.delay

class LearnerCodeDialog {

    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun DialogPreview() {
        LearnerCodeDialogScreen("NTR139", {  },{},{})
    }
    @Composable
    fun LearnerCodeDialogScreen(
        code : String,
        onClickCancel: () -> Unit,
        onClickRequest:() -> Unit,
        onClickCopy:(String) -> Unit
    ) {
        val sec = remember {
            mutableIntStateOf(180)
        }

        LaunchedEffect(Unit) {
            while (sec.value > 0) {
                delay(1000)
                sec.intValue -= 1
            }
        }

        Dialog(
            onDismissRequest = { onClickCancel() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Box(
                modifier = Modifier
                    .height(320.dp)
                    .width(520.dp)
                    .background(White, RoundedCornerShape(20.dp))// Card의 모든 꼭지점에 8.dp의 둥근 모서리 적용
            )
            {
                Column (
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "학습자 등록 코드",
                            fontSize = 20.sp,
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.dialog_cancel_ic),
                            contentDescription = null,
                            Modifier
                                .size(48.dp)
                                .clickWithScaleAnimation(onClickCancel)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()){
                        Text(text = code,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.W600,
                            color = Black)
                        Spacer(modifier = Modifier.width(10.dp))
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickWithScaleAnimation({ onClickCopy(code) })){
                            Image(painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = null)
                            Text(text = "복사하기",
                                color = Gray400)
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()){
                        Text(text = "유효시간",
                            color = Red,
                            fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = String.format("%02d:%02d", sec.intValue/60, sec.intValue%60),
                            color = Red,
                            fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(BoxBackground, RoundedCornerShape(8.dp))
                        .height(60.dp)
                        .clickWithScaleAnimation({
                            onClickRequest()
                            sec.intValue = 180
                        }),
                        contentAlignment = Alignment.Center) {
                        Text(text = "코드 다시 요청하기")
                    }
                }

            }
        }
    }
}