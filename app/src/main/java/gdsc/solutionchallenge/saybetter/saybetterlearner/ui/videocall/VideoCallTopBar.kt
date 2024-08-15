package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DeepDarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

@Composable
fun VideoCallTopbar(
    videoCallViewModel: VideoCallViewModel,
    clickBack:()->Unit,
    clickDeatil:()->Unit,
    isStart : Boolean,
    commOptCnt : Int,
    commOptTimes:Int) {

    val title = videoCallViewModel.title.collectAsState()
    val description = videoCallViewModel.description.collectAsState()
    val cnt = videoCallViewModel.commOptCnt.collectAsState()
    val iconState = videoCallViewModel.iconState.collectAsState()

    Row (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f)
        .padding(bottom = 20.dp)
        .background(DeepDarkGray),
        verticalAlignment = Alignment.CenterVertically){
        Row (
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                Modifier
                    .size(35.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = CustomClickEvent
                    ) {
                        clickBack()
                    })
            Row (modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically){
                if(!isStart) {
                    Text(
                        text = "최승아 교육자와 솔루션 진행",
                        color = Color.White,
                        fontSize = 20.sp,
                    )
                }
                if (isStart) {
                    Text(
                        text = title.value,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = description.value,
                        color = Color.Gray,
                        fontSize = 15.sp,
                    )
                }
            }

            Row (horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically){
                if(isStart ) {
                    Text(
                        text = "의사소통기회",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 5.dp)
                    )

                    Text(
                        text = "${commOptCnt}/${cnt.value}",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 5.dp)
                    )

                    Canvas(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                    ) {
                        drawLine(
                            color = Gray400,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2f
                        )
                    }

                    Text(
                        text = "타이머",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 5.dp)
                    )

                    Text(
                        text = String.format(
                            "%02d:%02d",
                            commOptTimes / 60,
                            commOptTimes % 60
                        ),
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
                if (isStart) {
                    Image(
                        painter = painterResource(id = if (iconState.value) R.drawable.ic_speaker_on else R.drawable.ic_speaker_off),
                        contentDescription = null,
                        Modifier
                            .size(35.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }

                Image(painter = painterResource(id = R.drawable.ic_detail),
                    contentDescription = null,
                    Modifier
                        .size(35.dp)
                        .clickable {
                            clickDeatil()
                        })
            }
        }
    }
}