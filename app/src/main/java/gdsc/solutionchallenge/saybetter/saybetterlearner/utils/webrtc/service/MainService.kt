package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.DataConverter
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType.*
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceActions.*
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.RTCAudioManager
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.VideoTextureViewRenderer
import org.webrtc.DataChannel
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject


@AndroidEntryPoint
class MainService : Service(), MainRepository.Listener {
    val TAG : String = "MainService"

    private var isServiceRunning = false
    private var userid : String? = null

    private lateinit var notificationManager : NotificationManager
    private lateinit var rtcAudioManager : RTCAudioManager

    @Inject lateinit var mainRepository : MainRepository

    companion object {
        var listener : CallEventListener? = null
        var endCallListener : EndCallListener? = null
        var interactionListener : InteractionListener? = null
        var localSurfaceView : SurfaceViewRenderer? = null
        var remoteSurfaceView : SurfaceViewRenderer? = null
    }


    //생성되면 NotificationManager 가져오기
    override fun onCreate() {
        super.onCreate()

        rtcAudioManager = RTCAudioManager.create(this)
        rtcAudioManager.setDefaultAudioDevice(RTCAudioManager.AudioDevice.SPEAKER_PHONE)

        notificationManager = getSystemService(
            NotificationManager::class.java
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { incomingIntent ->
            when(incomingIntent.action) {
                START_SERVICE.name -> handleStartService(incomingIntent)
                SETUP_VIEWS.name -> handleSetupViews(incomingIntent)
                END_CALL.name -> handleEndCall()
                SWITCH_CAMERA.name -> handleSwitchCamera()
                TOGGLE_AUDIO.name -> handleToggleAudio(incomingIntent)
                TOGGLE_VIDEO.name -> handleToggleVideo(incomingIntent)
                STOP_SERVICE.name -> handleStopService()
                else -> Unit
            }
        }
        return START_STICKY
    }

    private fun handleStopService() {
        mainRepository.endCall()
        mainRepository.logOff {
            isServiceRunning = false
            stopSelf()
        }
    }

    private fun handleToggleVideo(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        mainRepository.toggleVideo(shouldBeMuted)
    }

    private fun handleToggleAudio(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        mainRepository.toggleAudio(shouldBeMuted)
    }

    private fun handleSwitchCamera() {
        mainRepository.switchCamera()
    }

    private fun handleEndCall() {
        //1. we have to send a signal to other peer that call is ended
        mainRepository.sendEndCall()

        //2. end out call process and restart our webrtc client
        endCallAndRestartRepository()
    }

    private fun endCallAndRestartRepository() {
        mainRepository.endCall()
        endCallListener?.onCallEnded()
        mainRepository.initWebrtcClient(userid!!)
    }

    private fun handleSetupViews(incomingIntent: Intent) {
        val isCaller = incomingIntent.getBooleanExtra("isCaller", false)
        val target = incomingIntent.getStringExtra("target")

        mainRepository.setTarget(target!!)

        // Local, Remote SurfaceViewRenderer init
        mainRepository.initLocalSurfaceView(localSurfaceView!!)
        mainRepository.initRemoteSurfaceView(remoteSurfaceView!!)

        //Caller 가 아닐 경우 Call 시작
        if(!isCaller) {
            Log.d("MainService", "Start Call to $target")
            mainRepository.startCall()
        }
    }

    private fun handleStartService(incomingIntent: Intent) {
        //Service 시작 시점에서 toggle on
        if(!isServiceRunning) {
            isServiceRunning = true
            userid = incomingIntent.getStringExtra("userid")
            startServiceWithNotification()

            //setup my clients
            mainRepository.listener = this
            mainRepository.initFirebase()
            mainRepository.initWebrtcClient(userid!!)
        }
    }

    //Notification Manager에게 정의한 notificationChannel 전달하여 생성하기
    private fun startServiceWithNotification() {
        val notificationChannel = NotificationChannel(
            "channel1", "foreground", NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(
            this, "channel1"
        )

        startForeground(1, notification.build())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onLatestEventReceived(data: DataModel) {
        Log.d(TAG, "onLatestEventReceived: $data")
        listener?.onCallReceived(data)

    }

    override fun endCall() {
        //remote peer로부터 통화 종료 신호를 받은 경우
        endCallAndRestartRepository()
    }

    override fun onDataReceivedFromChannel(it: DataChannel.Buffer) {
        Log.d("DataChannel", "Data Received")

        // 여기서 case 나누어 처리
        val model = DataConverter.convertToModel(it)
        model?.let {
            if(it.first == "TEXT") {
                when(it.second) {
                    GREETING.name -> {
                        interactionListener?.onRemoteGreeting()
                    }
                    SWITCH_TO_LEARNING.name -> {
                        Log.d("DataChannel", SWITCH_TO_LEARNING.name)
                        interactionListener?.onSwitchToLearning()
                    }
                    SWITCH_TO_ENDING.name -> {
                        interactionListener?.onSwitchToEnding()
                    }
                    SWITCH_TO_LAYOUT_1.name -> {
                        interactionListener?.onSwitchToLayout1()
                    }
                    SWITCH_TO_LAYOUT_2.name -> {
                        interactionListener?.onSwitchToLayout2()
                    }
                    SWITCH_TO_LAYOUT_4.name -> {
                        interactionListener?.onSwitchToLayout4()
                    }
                    SWITCH_TO_LAYOUT_ALL.name -> {
                        interactionListener?.onSwitchToLayoutAll()
                    }
                    SCREEN_SHARE_TOGGLE_TRUE.name -> {
                        interactionListener?.onSetScreenSharing(true)
                    }
                    SCREEN_SHARE_TOGGLE_FALSE.name -> {
                        interactionListener?.onSetScreenSharing(false)
                    }

                    else -> {
                        val chunkedMessage: List<String> = it.second.toString().split(' ')
                        val action: String = chunkedMessage[0]

                        when(action){
                            SYMBOL_SELECT.name -> {
                                val symbolId: Int = chunkedMessage[1].toInt()
                                Log.d("DataChannel", "Action: SYMBOL_SELECT, id: $symbolId")
                                interactionListener?.onSymbolSelect(symbolId)
                            }
                            SYMBOL_DELETE.name -> {
                                val symbolId: Int = chunkedMessage[1].toInt()
                                Log.d("DataChannel", "Action: SYMBOL_DELETE, id: $symbolId")
                                interactionListener?.onSymbolDelete(symbolId)
                            }
                            SYMBOL_HIGHLIGHT.name -> {
                                val symbolId: Int = chunkedMessage[1].toInt()
                                Log.d("DataChannel", "Action: SYMBOL_HIGHLIGHT, id: $symbolId")
                                interactionListener?.onSymbolHighlight(symbolId)
                            }
                            ADD_TEXT_SYMBOL.name -> {
                                val symbolText: String = chunkedMessage[1]
                                Log.d("textSymbol", "[MainService] symbol text: $symbolText")
                                interactionListener?.onAddTextSymbol(symbolText)
                            }
                            else -> {
                                Log.d("DataChannel", "something wrong action")
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "received data is wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDataChannelReceived() {
        //DataChannel 감지한 경우
        Log.d("DataChannel", "Receive Data Channel")
    }

    //MainActivity에서 구현됨
    interface CallEventListener {
        fun onCallReceived(model: DataModel)
    }

    interface EndCallListener {
        fun onCallEnded()
    }

    interface InteractionListener {
        fun onRemoteGreeting()
        fun onSwitchToLearning()
        fun onSwitchToEnding()
        fun onSwitchToLayout1()
        fun onSwitchToLayout2()
        fun onSwitchToLayout4()
        fun onSwitchToLayoutAll()
        fun onSymbolSelect(symbolId: Int)
        fun onSymbolDelete(symbolId: Int)
        fun onSymbolHighlight(symbolId: Int)
        fun onSetScreenSharing(isScreenSharing: Boolean)
        fun onAddTextSymbol(symbolText: String)
    }


}


