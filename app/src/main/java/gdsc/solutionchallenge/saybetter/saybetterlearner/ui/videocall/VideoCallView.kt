package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.pretendardMediumFont
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.pretendardRegularFont
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.ChatInputBox
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
    toggleAudio: (Boolean) -> Unit,
    sendChatToPeer: (String) -> Unit,
    sendSymbolToPeer: (Int) -> Unit
) {

//    var isStart by remember { mutableStateOf(false) } //솔루션 시작?
    var ready by remember { mutableStateOf(false) } //대기 시간

    val isVideoOn by videoCallViewModel.isVideoOn.collectAsState()
    val isAudioOn by videoCallViewModel.isAudioOn.collectAsState()

    var commOptCnt by remember { mutableStateOf(videoCallViewModel.commOptCnt.value) }
    var commOptTimes by remember { mutableStateOf(videoCallViewModel.commOptTimes.value) }

    val selectedItemIndex by videoCallViewModel.selectedItemIndex.collectAsState()
    val cnt = videoCallViewModel.commOptCnt.collectAsState()

    val symbolRecord = videoCallViewModel.symbolRecord.collectAsState()

    val isStartLearning by videoCallViewModel.isStartLearning.collectAsState()
    val isEnding by videoCallViewModel.isEnding.collectAsState()
    val localGreetState by videoCallViewModel.localGreetState.collectAsState()
    val remoteGreetState by videoCallViewModel.remoteGreetState.collectAsState()
    val selectedSymbolList by videoCallViewModel.selectedSymbolList.collectAsState()
    val layoutState by videoCallViewModel.layoutState.collectAsState()
    val remoteSelectedSymbolId by videoCallViewModel.remoteSelectedSymbolId.collectAsState()
    val chatState by videoCallViewModel.chatState.collectAsState()
    val longChatText by videoCallViewModel.longChatText.collectAsState()
    val isScreenSharing by videoCallViewModel.isScreenSharing.collectAsState()

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
            videoCallViewModel.setSelectedItemIndex(null)
        }
    }

    Surface (){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VideoCallTopbar(
                videoCallViewModel = videoCallViewModel,
                clickBack = { onClickBack() },
                clickDeatil = { ready = true },
                isStart = !isReadyView,
                commOptCnt = commOptCnt,
                commOptTimes = commOptTimes
            )

            ReadyMainView(
                isCameraOn = !isVideoOn,
                videoCallViewModel = videoCallViewModel,
                isReadyView = isReadyView,
                isScreenSharing = isScreenSharing
            )

            if (isReadyView) {
                // 화면 공유중일 때는 채팅창 없어짐
                if (!isScreenSharing) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .height(80.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .clip(RoundedCornerShape(16.dp))
                                .background(White)
                        ) {
                            Text(
                                text = videoCallViewModel.getLongChatText(),
                                color = Color.Black,
                                fontFamily = FontFamily(pretendardRegularFont),
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ChatInputBox(
                            chatState = chatState,
                            onTextChange = { videoCallViewModel.setChatState(it) },
                            onChatSend = { sendChatToPeer(chatState) }
                        )
                    }
                }

                ReadyBottomMenuBar(
                    micClick = { toggleAudio(!isAudioOn) },
                    cameraClick = { toggleCamera(!isVideoOn) },
                    reverseClick = { switchCamera() },
                    greetClick = { greetClick() }
                )
            }else {
                // 화면 공유 중일 때는 learning view 안뜸
                if (!isScreenSharing) {
                    StartMainView(
                        symbolSet = symbolSet,
                        ttsManager = ttsManager,
                        commOptCnt = commOptCnt,
                        ready = ready,
                        selectedItemIndex = selectedItemIndex,
                        selectedSymbolList = selectedSymbolList,
                        cnt = cnt.value,
                        layoutState = layoutState,
                        selectedSymbolIdState = remoteSelectedSymbolId,
                        onSymbolClicked = { selectedItemIndex, symbol ->
                            videoCallViewModel.localSymbolHighlight(selectedItemIndex, symbol.title)
                            sendSymbolToPeer(symbol.id)
                        }
                    )
                }

                StartBottomMenuBar(
                    micClick = { toggleAudio(!isAudioOn) },
                    cameraClick = { toggleCamera(!isVideoOn) },
                    reverseClick = { switchCamera() }
                )
            }
        }
    }
}
