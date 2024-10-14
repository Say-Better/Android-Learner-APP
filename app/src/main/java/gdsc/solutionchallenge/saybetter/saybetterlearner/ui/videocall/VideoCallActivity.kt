package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.rememberGraphicsLayer
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.ViewModelProvider
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSListener
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceRepository
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType.GREETING
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import kotlinx.coroutines.CoroutineScope

const val TAG = "VideoCall"

@AndroidEntryPoint
class VideoCallActivity : ComponentActivity(), TTSListener, MainService.EndCallListener {

    private lateinit var ttsManager: TTSManager

    private var target: String? = null
    private var isCaller: Boolean = true

    @Inject lateinit var serviceRepository: MainServiceRepository
    @Inject lateinit var mainRepository: MainRepository
    private lateinit var videoCallViewModel: VideoCallViewModel

    private val symbolList = listOf(
        Symbol(0, "가요", R.drawable.ic_symbol_go),
        Symbol(1, "감격하다", R.drawable.ic_symbol_moved),
        Symbol(2, "놀라다", R.drawable.ic_symbol_surprised),
        Symbol(3, "따분하다", R.drawable.ic_symbol_boring),
        Symbol(4, "떨리다", R.drawable.ic_symbol_tremble),
        Symbol(5, "미안하다", R.drawable.ic_symbol_sorry),
        Symbol(6, "민망하다", R.drawable.ic_symbol_embarrassed),
        Symbol(7, "반가워요", R.drawable.ic_symbol_glad),
        Symbol(8, "밥", R.drawable.ic_symbol_rice),
        Symbol(9, "배고파요", R.drawable.ic_symbol_hungry),
        Symbol(10, "부끄러워요", R.drawable.ic_symbol_shy),
        Symbol(11, "부담스럽다", R.drawable.ic_symbol_pressured),
        Symbol(12, "부럽다", R.drawable.ic_symbol_envious),
        Symbol(13, "뿌듯하다", R.drawable.ic_symbol_plessure),
        Symbol(14, "상처받다", R.drawable.ic_symbol_hurt),
        Symbol(15, "속상해요", R.drawable.ic_symbol_upset),
        Symbol(16, "시작", R.drawable.ic_symbol_start),
        Symbol(17, "신나요", R.drawable.ic_symbol_excited),
        Symbol(18, "어리둥절하다", R.drawable.ic_symbol_confused),
        Symbol(19, "우울해요", R.drawable.ic_symbol_depressed),
        Symbol(20, "자랑스럽다", R.drawable.ic_symbol_proud),
        Symbol(21, "짜증나다", R.drawable.ic_symbol_annoying),
        Symbol(22, "화나요", R.drawable.ic_symbol_angry),
        Symbol(23, "흥미롭다", R.drawable.ic_symbol_interested),
    )  // 임시로 10개의 아이템을 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ttsManager = TTSManager(this@VideoCallActivity)
        ttsManager.setTTSListener(this)

        videoCallViewModel =  ViewModelProvider(this).get(VideoCallViewModel::class.java)

        init()
        setContent {
            VideoCallView(
                videoCallViewModel = videoCallViewModel,
                onClickBack = {
                    serviceRepository.sendEndCall()
                },
                ttsManager = ttsManager,
                symbolSet = symbolList,
                greetClick = {
                    videoCallViewModel.localGreeting()
                    mainRepository.sendTextToDataChannel(GREETING.name)
                },
                switchCamera = { serviceRepository.switchCamera() },
                toggleCamera = { toggle ->
                    serviceRepository.toggleVideo(toggle)
                    videoCallViewModel.setVideoState(toggle)
                               },
                toggleAudio = { toggle ->
                    serviceRepository.toggleAudio(toggle)
                    videoCallViewModel.setAudioState(toggle)
                }
            )
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
        videoCallViewModel.initVideoCall("TV 보는 상황 솔루션", "", "중재 단계 5회기", 5, symbolList.size, symbolList, ttsManager)
        MainService.endCallListener = this
    }

    private fun saveClickLog(symbol : Symbol?) {
        videoCallViewModel.addRecord(symbol)
    }

    override fun onTTSStarted() {
        // Update iconState to true when TTS starts
        videoCallViewModel.setIconState(true)
    }

    override fun onTTSStopped() {
        // Update iconState to false when TTS stops
        videoCallViewModel.setIconState(false)
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
