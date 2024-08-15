package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

const val TAG = "VideoCall"

@AndroidEntryPoint
class VideoCallActivity : ComponentActivity(), TTSListener {

    private lateinit var ttsManager: TTSManager

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
            VideoCallView(
                videoCallViewModel = videoCallViewModel,
                saveClickLog = {col, index ->
                    saveClickLog(symbolSet[col].getOrNull(index))
                },
                onClickBack = {finish()},
                ttsManager = ttsManager,
                symbolSet = symbolSet)
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
}
