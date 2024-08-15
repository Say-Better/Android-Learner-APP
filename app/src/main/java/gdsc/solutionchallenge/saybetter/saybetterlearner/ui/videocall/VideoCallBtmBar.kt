package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.GrayW40
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

@Composable
fun ReadyBottomMenuBar(
    micClick:()->Unit,
    cameraClick:()->Unit,
    reverseClick:()->Unit,
    greetClick:()->Unit) {

    var micClicked: Boolean by remember{ mutableStateOf(false) }
    var cameraClicked: Boolean by remember{ mutableStateOf(false) }

    Row (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(top = 30.dp),
        horizontalArrangement = Arrangement.Center,){
        Box (modifier = Modifier
            .background(DarkGray, RoundedCornerShape(36.dp))
            .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                micClicked = !micClicked // 클릭 시 상태 변경
                micClick()
            }){
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = if (micClicked) "음소거 해제" else "음소거 하기",
                    color = Color.White,
                    fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.width(20.dp))

        Box (modifier = Modifier
            .background(DarkGray, RoundedCornerShape(36.dp))
            .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                cameraClicked = !cameraClicked
                cameraClick()
            }){
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = if (cameraClicked) "카메라 끄기" else "카메라 키기",
                    color = Color.White,
                    fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Box (modifier = Modifier
            .background(DarkGray, RoundedCornerShape(36.dp))
            .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                reverseClick()
            }){
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.ic_camera_reverse),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "카메라 전환",
                    color = Color.White,
                    fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.width(20.dp))
        Box (modifier = Modifier
            .background(MainGreen, RoundedCornerShape(36.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                greetClick()
            }){
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.ic_waving_hand),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "인사하기",
                    color = Color.White,
                    fontSize = 20.sp)
            }
        }
    }
}
@Composable
fun StartBottomMenuBar(
    micClick:()->Unit,
    cameraClick:()->Unit,
    reverseClick:()->Unit,
    isCameraOn : Boolean
) {
    var micClicked: Boolean by remember{ mutableStateOf(false) }
    var cameraClicked: Boolean by remember{ mutableStateOf(false) }
    Row (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f),
        verticalAlignment = Alignment.CenterVertically) {
        Row (horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)){
            Box(modifier = Modifier
                .background(DarkGray, RoundedCornerShape(36.dp))
                .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    micClicked = !micClicked // 클릭 시 상태 변경
                    micClick()
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_mic),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = if (micClicked) "음소거 해제" else "음소거 하기",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(modifier = Modifier
                .background(DarkGray, RoundedCornerShape(36.dp))
                .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    cameraClicked = !cameraClicked
                    cameraClick()
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = if (cameraClicked) "카메라 끄기" else "카메라 키기",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(modifier = Modifier
                .background(DarkGray, RoundedCornerShape(36.dp))
                .border(2.dp, GrayW40, RoundedCornerShape(36.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    reverseClick()
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera_reverse),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "카메라 전환",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
            Row (horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f),) {
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier = Modifier
                    .background(MainGreen, RoundedCornerShape(36.dp))
                    .padding(horizontal = 30.dp, vertical = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = CustomClickEvent
                    ) {
                        reverseClick()
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_correct),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "네",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier = Modifier
                    .background(Red, RoundedCornerShape(36.dp))
                    .padding(horizontal = 30.dp, vertical = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = CustomClickEvent
                    ) {
                        reverseClick()
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_incorrect),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "아니오",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
        //여기에 의사소통 버튼
        Row (modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)){
            //webRTC 넣는 곳
            if (isCameraOn) {
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .width(250.dp))
            }else {
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .width(250.dp))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Image(painter = painterResource(id = R.drawable.rectangle_1638),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.7f))
        }
    }
}