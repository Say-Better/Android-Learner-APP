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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.symbollayout.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSListener
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DeepDarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.GrayW40
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Transparent
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.symbollayout.ReadySymbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceRepository
import kotlinx.coroutines.delay
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

const val TAG = "VideoCall"

@AndroidEntryPoint
class VideoCallActivity : ComponentActivity(), TTSListener, MainService.EndCallListener {

    private lateinit var ttsManager: TTSManager
    private var iconState by mutableStateOf(false)

    private var target: String? = null
    private var isCaller: Boolean = true

    @Inject lateinit var serviceRepository: MainServiceRepository
    private lateinit var videoCallViewModel: VideoCallViewModel

    private val items = listOf(
        Symbol(1,"1인분주세요", R.drawable.a1),
        Symbol(2,"2인분주세요", R.drawable.a2),
        Symbol(3,"3인분주세요", R.drawable.a3),
        Symbol(4,"가르쳐주세요", R.drawable.a4),
        Symbol(5,"간식", R.drawable.a5),
        Symbol(6,"과자", R.drawable.a6),
        Symbol(7,"과자주세요", R.drawable.a7),
        Symbol(8,"그만먹을래요", R.drawable.a8),
        Symbol(9,"배고파요", R.drawable.a9),
        Symbol(10,"배달음식", R.drawable.a10),
        Symbol(11,"배불러요", R.drawable.a11),
        Symbol(12,"식혀주세요", R.drawable.a12),
        Symbol(13,"양념치킨", R.drawable.a13),
        Symbol(14,"양념해주세요", R.drawable.a14),
        Symbol(15,"어떤 음식 좋아해요", R.drawable.a15),
        Symbol(16,"오늘의 음식은 무엇인가요", R.drawable.a16),
        Symbol(17,"음식", R.drawable.a17),
        Symbol(18,"음식값이 싸요", R.drawable.a18),
        Symbol(19,"이거 먹을래요", R.drawable.a19),
        Symbol(20,"잘라주세요", R.drawable.a20),
        Symbol(21,"정리해주세요", R.drawable.a21),
        Symbol(22,"집에서 먹을래요", R.drawable.a22),
        Symbol(23,"치킨", R.drawable.a23),
        Symbol(24,"치킨 시켜주세요", R.drawable.a24),
        Symbol(25,"피자", R.drawable.a25),
        Symbol(26,"피자 시켜주세요", R.drawable.a26),
    )  // 임시로 10개의 아이템을 생성

    private val symbolSet : List<List<Symbol>> = listOf(
        listOf(items[0]),
        listOf(items[1], items[2]),
        listOf(items[3],items[4],items[5],items[6]),
        listOf(items[7],items[8],items[9],items[10],items[11],items[12],items[13],items[14],items[15],items[16],)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsManager = TTSManager(this@VideoCallActivity)
        ttsManager.setTTSListener(this)

        videoCallViewModel =  ViewModelProvider(this).get(VideoCallViewModel::class.java)

        init()
        setContent {
            VideoCallView()
        }
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

        //초기화
        videoCallViewModel.initVideoCall("TV 보는 상황 솔루션", "", "중재 단계 5회기", 5, symbolSet.size, symbolSet)
        MainService.endCallListener = this

    }

    @Composable
    fun LocalVideoRenderer(modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = {
                MainService.localSurfaceView!!
            },
            update = { surfaceViewRenderer ->
                // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
            }
        )
    }

    @Composable
    fun RemoteVideoRenderer(modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = {
                MainService.remoteSurfaceView!!
            },
            update = { surfaceViewRenderer ->
                // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
            }
        )
    }

    @Composable
    fun VideoCallView() {
        var isStart by remember { mutableStateOf(false) } //솔루션 시작?
        var ready by remember { mutableStateOf(false) } //대기 시간

        var isCameraOn by remember {
            mutableStateOf(true)
        }
        var commOptCnt by remember { mutableStateOf(videoCallViewModel.commOptCnt.value) }
        var commOptTimes by remember { mutableStateOf(videoCallViewModel.commOptTimes.value) }

        val selectedItemIndex = remember { mutableStateOf<Int?>(null) }
        val cnt = videoCallViewModel.commOptCnt.collectAsState()

        val symbolRecord = videoCallViewModel.symbolRecord.collectAsState()

        if (isStart && ready) { //솔루션 시작 + 대기 아닐시
            LaunchedEffect(Unit) {
                while (commOptTimes > 0) {
                    delay(1000L) // 1초 지연
                    commOptTimes-- // 값을 1 감소시킴
                }
                commOptTimes = videoCallViewModel.commOptTimes.value
                if (commOptCnt > 1) {
                    commOptCnt--
                }else {
                    Log.d("clickLog", symbolRecord.toString())
                }
                ready = false
                val selectedIndex = selectedItemIndex.value
                selectedItemIndex.value = null

                // 클릭 로그 저장
                saveClickLog(
                    selectedIndex?.let { index ->
                        symbolSet[cnt.value - commOptCnt].getOrNull(index)
                    }
                )

            }
        }

        Surface (){
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally) {
                VideoCallTopbar(clickBack = {
                    serviceRepository.sendEndCall()
                }, clickDeatil = {
                    ready = true    //의사소통 시작 -> 차후 코드 변경 필요
                }, isStart, iconState, commOptCnt, commOptTimes)
                if (!isStart) {
                    ReadyMainScreen(isCameraOn)
                    ReadyBottomMenuBar(
                        micClick = {},
                        cameraClick = {
                            isCameraOn = if(isCameraOn) false
                            else true
                        },
                        reverseClick = {
                            serviceRepository.switchCamera()
                        },
                        greetClick = {isStart = true})
                }else {
                    StartMainScreen(iconState, commOptCnt = commOptCnt, ready, selectedItemIndex, cnt.value) { newState ->
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

                        },
                        isCameraOn = isCameraOn
                    )
                }
            }
        }
    }

    @Composable
    fun VideoCallTopbar(clickBack:()->Unit,
                        clickDeatil:()->Unit,
                        isStart : Boolean,
                        iconState: Boolean,
                        commOptCnt : Int,
                        commOptTimes:Int) {
        val title = videoCallViewModel.title.collectAsState()
        val description = videoCallViewModel.description.collectAsState()
        val cnt = videoCallViewModel.commOptCnt.collectAsState()

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
                            text = "송승아 교육자와 솔루션 진행",
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
    fun ReadyMainScreen(isCameraOn : Boolean) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
            contentAlignment = Alignment.Center){
                Row (modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .background(Transparent, RoundedCornerShape(0.dp)),
                    verticalAlignment = Alignment.CenterVertically){

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(width = 622.dp, height = 370.dp)
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        LocalVideoRenderer(
                            modifier = Modifier
                                .size(width = 622.dp, height = 370.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(width = 622.dp, height = 370.dp)
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        RemoteVideoRenderer(
                            modifier = Modifier
                                .size(width = 622.dp, height = 370.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
        }
    }

    @Composable
    fun StartMainScreen(iconState: Boolean,
                        commOptCnt: Int,
                        ready : Boolean,
                        selectedItemIndex:MutableState<Int?>,
                        cnt :Int,
                        onSymbolClick: (Boolean) -> Unit, ) {

        Row (modifier = Modifier
            .fillMaxHeight(0.82f)
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically){
            if (symbolSet[cnt - commOptCnt].size <=2) {
                symbolSet[cnt - commOptCnt].forEachIndexed{ index, item ->
                    if(ready) {
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            isSelected = index == selectedItemIndex.value,
                            symbol = item,
                            onSymbolClick = {
                                selectedItemIndex.value = index
                                ttsManager.speak(item.title)
                            },
                        )
                    }else {
                        ReadySymbol(modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),)
                    }
                }
            }else if (symbolSet[cnt - commOptCnt].size == 4) {
                symbolSet[cnt - commOptCnt].forEachIndexed{ index, item ->
                    if(ready) {
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),
                            isSelected = index == selectedItemIndex.value,
                            symbol = item,
                            onSymbolClick = {
                                selectedItemIndex.value = index
                                ttsManager.speak(item.title)
                            },
                        )
                    }else {
                        ReadySymbol(modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp),)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5), // 한 행에 4개의 아이템을 배치
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(symbolSet[cnt - commOptCnt]) { index, item ->
                        if(ready) {
                            Symbol(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                                    .height(250.dp),
                                isSelected = index == selectedItemIndex.value,
                                symbol = item,
                                onSymbolClick = {
                                    selectedItemIndex.value = index
                                    ttsManager.speak(item.title)
                                },
                            )
                        }else {
                            ReadySymbol(modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),)
                        }
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

    private fun saveClickLog(symbol : Symbol?) {
        videoCallViewModel.addRecord(symbol)
    }

    override fun onTTSStarted() {
        // Update iconState to true when TTS starts
        iconState = true
    }

    override fun onTTSStopped() {
        // Update iconState to false when TTS stops
        iconState = false
    }

    override fun updateIndex(start: Int, end: Int) {

    }

    override fun onCallEnded() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        MainService.remoteSurfaceView?.release()
        MainService.remoteSurfaceView = null

        MainService.localSurfaceView?.release()
        MainService.localSurfaceView = null
    }
}
