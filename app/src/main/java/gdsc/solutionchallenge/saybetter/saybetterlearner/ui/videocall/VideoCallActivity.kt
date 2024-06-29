package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.viewinterop.AndroidView
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.CamCoder.CameraComponet
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.SymbolLayout.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.TTS.TTSListener
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.TTS.TTSManager
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DeepDarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.GrayW40
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Transparent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.Customclick.CustomClickEvent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.VideoTextureViewRenderer
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.WebRTCClient
import org.webrtc.MediaStream
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoTrack
import javax.inject.Inject

const val TAG = "VideoCall"

@AndroidEntryPoint
class VideoCallActivity : ComponentActivity(), TTSListener {

    private lateinit var ttsManager: TTSManager
    private var iconState by mutableStateOf(false) // 이 부분을 추가해주세요.

    private var target: String? = null
    private var isCaller: Boolean = true

    @Inject lateinit var serviceRepository: MainServiceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsManager = TTSManager(this@VideoCallActivity, this)
        setContent {
            VideoCallView()
        }
        init()
    }

    private fun init() {
        intent.getStringExtra("target")?.let {
            this.target = it
        }?: kotlin.run {
            finish()
        }

        isCaller = intent.getBooleanExtra("isCaller", true)

        // MainService에서 SurfaceView를 관리하도록 위임
        MainService.localSurfaceView = SurfaceViewRenderer(this)
        MainService.remoteSurfaceView = SurfaceViewRenderer(this)

        // Activity에 표시될 SurfaceViewRenderer를 MainService 멤버변수에 연결하고 serviceRepo를 통해 초기화하도록 명령
        serviceRepository.setupViews(isCaller, target!!)

    }

    @Composable
    fun VideoCallView() {
        var isStart by remember { mutableStateOf(false) }
        var cameraSelectorState by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
        var isCameraOn by remember {
            mutableStateOf(true)
        }
        Surface (){
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally) {
                VideoCallTopbar(clickBack = {
                    finish()
                }, clickDeatil = {

                }, isStart, iconState)
                if (!isStart) {
                    ReadyMainScreen(cameraSelectorState, isCameraOn)
                    ReadyBottomMenuBar(
                        micClick = {},
                        cameraClick = {
                            isCameraOn = if(isCameraOn) false
                            else true
                        },
                        reverseClick = {
                            cameraSelectorState = if (cameraSelectorState == CameraSelector.DEFAULT_BACK_CAMERA)
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            else
                                CameraSelector.DEFAULT_BACK_CAMERA
                        },
                        greetClick = {isStart = true})
                }else {
                    StartMainScreen(iconState) { newState ->
                        iconState = newState // 아이콘 상태 업데이트
                    }
                    StartBottomMenuBar(
                        micClick = {},
                        cameraClick = {
                            isCameraOn = if(isCameraOn) false
                            else true
                        },
                        reverseClick = {
                            isStart = false
                            cameraSelectorState = if (cameraSelectorState == CameraSelector.DEFAULT_BACK_CAMERA)
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            else
                                CameraSelector.DEFAULT_BACK_CAMERA
                        },
                        cameraSelectorState = cameraSelectorState,
                        isCameraOn = isCameraOn)
                }
            }
        }
    }

    @Composable
    fun VideoCallTopbar(clickBack:()->Unit, clickDeatil:()->Unit, isStart : Boolean, iconState: Boolean ) {
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
                            text = "홍길동 교육자와 솔루션 진행",
                            color = Color.White,
                            fontSize = 20.sp,
                        )

                    }
                    if (isStart) {

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
                            modifier = Modifier.padding(end = 20.dp)
                        )
                    }
                    if (isStart) {
                        Image(
                            painter = painterResource(id = if (iconState) R.drawable.ic_speaker_on else R.drawable.ic_speaker_off),
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

    @Composable
    fun ReadyMainScreen(cameraSelectorState : CameraSelector, isCameraOn : Boolean) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
            contentAlignment = Alignment.Center){
                Row (modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .background(Transparent, RoundedCornerShape(0.dp)),
                    verticalAlignment = Alignment.CenterVertically){
                    if (isCameraOn) {
                        CameraComponet(
                            context = this@VideoCallActivity,
                            modifier = Modifier
                                .weight(1f),
                            cameraSelectorState = cameraSelectorState
                        )
                    }else {
                        Image(painter = painterResource(id = R.drawable.rectangle_1638),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f))
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Image(painter = painterResource(id = R.drawable.rectangle_1638),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f))
                }
        }
    }

    @Composable
    fun StartMainScreen(iconState: Boolean, onSymbolClick: (Boolean) -> Unit) {
        val items = List(10) { it } // 임시로 10개의 아이템을 생성
        val selectedItemIndex = remember { mutableStateOf<Int?>(null) }

        Row (modifier = Modifier
            .fillMaxHeight(0.82f)
            .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically){
            if (items.size <=2) {
                items.forEach{ i->
                    Symbol(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp),
                        isSelected = i == selectedItemIndex.value,
                        onSymbolClick = {
                            selectedItemIndex.value = i
                        },
                        context = this@VideoCallActivity
                    )
                }
            }else if (items.size == 4) {
                items.forEach{ i->
                    Symbol(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp),
                        isSelected = i == selectedItemIndex.value,
                        onSymbolClick = {
                            selectedItemIndex.value = i
                        },
                        context = this@VideoCallActivity
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5), // 한 행에 4개의 아이템을 배치
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(items) { index, _ ->
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),
                            isSelected = index == selectedItemIndex.value,
                            onSymbolClick = {
                                selectedItemIndex.value = index
                                ttsManager.speak("학교가요 밥먹으러")
                            },
                            context = this@VideoCallActivity
                        )
                    }
                }
            }
        }


    }


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
    fun StartBottomMenuBar(micClick:()->Unit,
                           cameraClick:()->Unit,
                           reverseClick:()->Unit,
                           cameraSelectorState : CameraSelector,
                           isCameraOn : Boolean
                           ) {
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
                if (isCameraOn) {
                    CameraComponet(
                        context = this@VideoCallActivity,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .width(150.dp),
                        cameraSelectorState = cameraSelectorState
                    )
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

    private fun SaveClickLog(symbol : Symbol) {
        //뷰모델로 관리 -> 클릭하면 이 함수를 호출해서 뷰모델의 list 데이터열에 추가!
    }

    override fun onTTSStarted() {
        // Update iconState to true when TTS starts
        iconState = true
    }

    override fun onTTSStopped() {
        // Update iconState to false when TTS stops
        iconState = false
    }
}
