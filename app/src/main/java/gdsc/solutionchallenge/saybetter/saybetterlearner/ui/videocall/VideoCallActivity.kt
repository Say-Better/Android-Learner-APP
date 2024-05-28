package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.symbolLayout.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DeepDarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.GrayW40
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

class VideoCallActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewPreview()
        }
    }
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun ViewPreview() {
        var isStart by remember { mutableStateOf(true) }
        Surface (){
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally) {
                VideoCallTopbar(clickBack = {
                    finish()
                }, clickDeatil = {

                }, isStart)
                if (!isStart) {
                    ReadyMainScreen()
                    ReadyBottomMenuBar(
                        micClick = {},
                        cameraClick = {},
                        reverseClick = {},
                        greetClick = {isStart = true})
                }else {
                    StartMainScreen()
                    StartBottomMenuBar(
                        micClick = {},
                        cameraClick = {},
                        reverseClick = {isStart = false})
                }
            }
        }
    }

    @Composable
    fun VideoCallTopbar(clickBack:()->Unit, clickDeatil:()->Unit, isStart : Boolean) {
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
                    Text(text = "홍길동 교육자와 솔루션 진행",
                        color = Color.White,
                        fontSize = 20.sp,)

                    if (isStart) {

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
                            text = "TV 보는 상황 솔루션",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "중재 단계 5회기",
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
                            text = "0/10",
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
                            text = "00:10",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 60.dp)
                        )
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

    @Composable
    fun ReadyMainScreen() {
        Box (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
            contentAlignment = Alignment.Center){
            Row (modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(DeepDarkGray, RoundedCornerShape(24.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =  Arrangement.Center,){
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.48f))
                Spacer(modifier = Modifier.fillMaxWidth(0.07f))//임시

                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth())

            }

        }
    }

    @Composable
    fun StartMainScreen() {
        val items = List(10) { it } // 임시로 10개의 아이템을 생성

        Row (modifier = Modifier
            .fillMaxHeight(0.8f)
            .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically){
            if (items.size <=2) {
                items.forEach{ i->
                    Symbol(modifier = Modifier.padding(8.dp).width(500.dp).height(580.dp))
                }
            }else if (items.size == 4) {
                items.forEach{ i->
                    Symbol(modifier = Modifier.padding(8.dp).weight(1f).height(340.dp))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5), // 한 행에 4개의 아이템을 배치
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items.size) { index ->
                        Symbol(modifier = Modifier.padding(8.dp).weight(1f).height(250.dp))
                    }
                }
            }
        }


    }


    @Composable
    fun ReadyBottomMenuBar(micClick:()->Unit,
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
            Box (modifier = androidx.compose.ui.Modifier
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

            Box (modifier = androidx.compose.ui.Modifier
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
            Box (modifier = androidx.compose.ui.Modifier
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
    fun StartBottomMenuBar(micClick:()->Unit,
                           cameraClick:()->Unit,
                           reverseClick:()->Unit) {
        var micClicked: Boolean by remember{ mutableStateOf(false) }
        var cameraClicked: Boolean by remember{ mutableStateOf(false) }
        Row (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically) {
            Row (horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)){
                Box(modifier = androidx.compose.ui.Modifier
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

                Box(modifier = androidx.compose.ui.Modifier
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
                Box(modifier = androidx.compose.ui.Modifier
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
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f))

                Spacer(modifier = Modifier.width(10.dp))

                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f))
            }
        }
    }


}