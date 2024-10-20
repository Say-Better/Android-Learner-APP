package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository

import android.util.Log
import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.firebaseClient.FirebaseClient
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType.Answer
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType.EndCall
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType.IceCandidates
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType.Offer
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.UserStatus
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.DataConverter
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.FileMetaDataType
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.MyPeerObserver
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.VideoTextureViewRenderer
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.WebRTCClient
import org.webrtc.DataChannel
import org.webrtc.EglBase
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient : FirebaseClient,
    private val webRTCClient: WebRTCClient,
    private val gson : Gson
) : WebRTCClient.Listener, WebRTCClient.ReceiverListener {
    var listener : Listener? = null
    private var target : String? = null
    private var remoteView: SurfaceViewRenderer? = null
    private var dataChannel: DataChannel? = null

    // webrtc client 초기화할때 init
    private var mediaStream: MediaStream? = null

    val TAG = "DataChannel"


    fun login(userid : String, isDone : (Boolean, String?) -> Unit) {
        firebaseClient.login(userid, isDone)
    }

    fun setTarget(target: String) {
        this.target = target
    }

    fun initFirebase() {
        firebaseClient.subscribeForLatestEvent(object : FirebaseClient.Listener {
            override fun onLatestEventReceived(event: DataModel) {
                listener?.onLatestEventReceived(event)
                when(event.type) {
                    Offer -> {
                        // Offer 받으면 SessionDescription 생성
                        webRTCClient.onRemoteSessionReceived(
                            SessionDescription(
                                SessionDescription.Type.OFFER,
                                event.data.toString()
                            )
                        )
                        // Offer 받았으므로 다시 Answer 전달해야함
                        webRTCClient.answer(target!!)
                    }
                    Answer -> {
                        // 전화를 거는 입장에서 회신을 받은 상황, Answer 형태의 SessionDescription 생성
                        webRTCClient.onRemoteSessionReceived(
                            SessionDescription(
                                SessionDescription.Type.ANSWER,
                                event.data.toString()
                            )
                        )
                    }
                    IceCandidates -> {
                        val candidate: IceCandidate? = try {
                            gson.fromJson(event.data.toString(), IceCandidate::class.java)
                        } catch (e: Exception) {
                            null
                        }
                        candidate?.let {
                            webRTCClient.addIceCandidateToPeer(it)
                        }
                    }
                    EndCall -> {
                        listener?.endCall()
                    }
                    else -> Unit
                }
            }

        })
    }

    fun initWebrtcClient(userid: String) {
        webRTCClient.listener = this
        webRTCClient.receiverListener = this
        webRTCClient.initializeWebrtcClient(userid, object : MyPeerObserver() {
            override fun onAddStream(p0: MediaStream?) {
                super.onAddStream(p0)
                try {
                    p0?.videoTracks?.get(0)?.addSink(remoteView)

                    // 이후 VideoTrack 다시 얻기위해 저장
                    mediaStream = p0
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onIceCandidate(p0: IceCandidate?) {
                super.onIceCandidate(p0)
                p0?.let {
                    webRTCClient.sendIceCandidate(target!!, it)
                }
            }

            override fun onConnectionChange(newState: PeerConnection.PeerConnectionState?) {
                super.onConnectionChange(newState)
                if(newState == PeerConnection.PeerConnectionState.CONNECTED) {
                    Log.d(TAG, "PeerConnection CONNECTED")
                    // 1. change my status to in call
                    changeMyStatus(UserStatus.IN_CALL)
                    // 2. clear latest event inside my user section in firebase database
                    firebaseClient.clearLatestEvent()

                    // 3. data channel 뚫기
//                    initDataChannel()
                }
            }

            override fun onDataChannel(p0: DataChannel?) {
                super.onDataChannel(p0)
                dataChannel = p0
                listener?.onDataChannelReceived()
            }
        })

    }

    // firebase에서의 상태를 업데이트
    private fun changeMyStatus(status : UserStatus) {
        firebaseClient.changeMyStatus(status)
    }

    fun sendConnectionRequest(target : String, success : (Boolean) -> Unit) {
        firebaseClient.sendMessageToOtherClient(
            DataModel(
                type = DataModelType.StartVideoCall,
                target = target
            ), success
        )
    }
    fun initLocalSurfaceView(view: SurfaceViewRenderer) {
        webRTCClient.initLocalSurfaceView(view)
    }
    fun initRemoteSurfaceView(view: SurfaceViewRenderer) {
        webRTCClient.initRemoteSurfaceView(view)
        this.remoteView = view
    }

    fun sendTextToDataChannel(text:String){
        sendBufferToDataChannel(DataConverter.convertToBuffer(FileMetaDataType.META_DATA_TEXT,text))
        sendBufferToDataChannel(DataConverter.convertToBuffer(FileMetaDataType.TEXT,text))
    }

    private fun sendBufferToDataChannel(buffer: DataChannel.Buffer){
        dataChannel?.send(buffer)
    }

    interface Listener {
        fun onLatestEventReceived(data : DataModel)
        fun endCall()
        fun onDataReceivedFromChannel(it: DataChannel.Buffer)
        fun onDataChannelReceived()
    }

    override fun onTransferEventToSocket(data: DataModel) {
        firebaseClient.sendMessageToOtherClient(data){}
    }

    fun startCall() {
        webRTCClient.call(target!!)
    }

    fun sendEndCall() {
        onTransferEventToSocket(
            DataModel(
                type = EndCall,
                target = target!!
            )
        )
    }

    fun logOff(function: () -> Unit) = firebaseClient.logOff(function)

    fun endCall() {
        webRTCClient.closeConnection()
        //통화 종료시 다시 온라인 상태로 돌아감
        changeMyStatus(UserStatus.ONLINE)
    }

    fun switchCamera() {
        webRTCClient.switchCamera()
    }

    fun toggleAudio(shouldBeMuted : Boolean) {
        webRTCClient.toggleAudio(shouldBeMuted)
    }

    fun toggleVideo(shouldBeMuted: Boolean) {
        webRTCClient.toggleVideo(shouldBeMuted)
    }

    override fun onDataReceived(it: DataChannel.Buffer) {
        Log.d("DataChannel", "data receive in mainRepo")
        listener?.onDataReceivedFromChannel(it)
    }
}