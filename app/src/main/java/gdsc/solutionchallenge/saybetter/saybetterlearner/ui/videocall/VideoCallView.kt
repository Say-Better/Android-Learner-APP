package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager
import kotlinx.coroutines.delay

@Composable
fun VideoCallView(
    videoCallViewModel: VideoCallViewModel,
    symbolSet : List<Symbol>,
    ttsManager: TTSManager,
    onClickBack:()->Unit,
    greetClick: () -> Unit,
    switchCamera: () -> Unit,
    toggleCamera: (Boolean) -> Unit,
    toggleAudio: (Boolean) -> Unit
) {

//    var isStart by remember { mutableStateOf(false) } //솔루션 시작?
    var ready by remember { mutableStateOf(false) } //대기 시간

    val isVideoOn by videoCallViewModel.isVideoOn.collectAsState()
    val isAudioOn by videoCallViewModel.isAudioOn.collectAsState()

    var commOptCnt by remember { mutableStateOf(videoCallViewModel.commOptCnt.value) }
    var commOptTimes by remember { mutableStateOf(videoCallViewModel.commOptTimes.value) }

    val selectedItemIndex = remember { mutableStateOf<Int?>(null) }
    val cnt = videoCallViewModel.commOptCnt.collectAsState()

    val symbolRecord = videoCallViewModel.symbolRecord.collectAsState()

    val isStartLearning by videoCallViewModel.isStartLearning.collectAsState()
    val isEnding by videoCallViewModel.isEnding.collectAsState()
    val localGreetState by videoCallViewModel.localGreetState.collectAsState()
    val remoteGreetState by videoCallViewModel.remoteGreetState.collectAsState()
    val selectedSymbolList by videoCallViewModel.selectedSymbolList.collectAsState()
    val layoutState by videoCallViewModel.layoutState.collectAsState()
    val remoteSelectedSymbolId by videoCallViewModel.remoteSelectedSymbolId.collectAsState()

    val isReadyView: Boolean = !(isStartLearning xor isEnding)

    if (!isReadyView && ready) { //솔루션 시작 + 대기 아닐시
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
//            val selectedIndex = selectedItemIndex.value// 클릭 로그 저장
//            saveClickLog(cnt.value - commOptCnt, selectedIndex!!)
            selectedItemIndex.value = null
        }
    }

    Surface (){
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally) {
            VideoCallTopbar(
                videoCallViewModel = videoCallViewModel,
                clickBack = { onClickBack() },
                clickDeatil = { ready = true },
                isStart = !isReadyView,
                commOptCnt = commOptCnt,
                commOptTimes = commOptTimes
            )

            if (isReadyView) {
                ReadyMainView(
                    isCameraOn = !isVideoOn,
                    videoCallViewModel = videoCallViewModel
                )

                ReadyBottomMenuBar(
                    micClick = { toggleAudio(!isAudioOn) },
                    cameraClick = { toggleCamera(!isVideoOn) },
                    reverseClick = { switchCamera() },
                    greetClick = { greetClick() }
                )

            }else {
                StartMainView(
                    symbolSet = symbolSet,
                    ttsManager = ttsManager,
                    commOptCnt = commOptCnt,
                    ready = ready,
                    selectedItemIndex = selectedItemIndex,
                    selectedSymbolList = selectedSymbolList,
                    cnt = cnt.value,
                    layoutState = layoutState,
                    selectedSymbolIdState = remoteSelectedSymbolId
                    )
                StartBottomMenuBar(
                    micClick = { toggleAudio(!isAudioOn) },
                    cameraClick = { toggleCamera(!isVideoOn) },
                    reverseClick = { switchCamera() },
                    isCameraOn = !isVideoOn
                )
            }
        }
    }
}
