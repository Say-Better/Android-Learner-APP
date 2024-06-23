package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient
//
import android.content.Context
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.RendererCommon
import org.webrtc.VideoTrack
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRTCClient @Inject constructor(
    private val context : Context,
    private val gson : Gson
) {
    // class variables
    val listener: Listener? = null
    private lateinit var userid: String

    // webrtc variables
    private val eglBaseContext = EglBase.create().eglBaseContext
    private val peerConnectionFactory by lazy { createPeerConnectionFactory() }
    private var peerConnection: PeerConnection? = null
    private val iceServer = listOf(
        PeerConnection.IceServer.builder("")
            .setUsername("")
            .setPassword("").createIceServer()
    )

    // call variables
    private lateinit var localSurfaceView : VideoTextureViewRenderer
    private lateinit var remoteSurfaceView : VideoTextureViewRenderer

    init {
        initPeerConnectionFactory()
    }

    private fun initPeerConnectionFactory() {
        val options = PeerConnectionFactory.InitializationOptions.builder(context)
            .setEnableInternalTracer(true).setFieldTrials("WebRTC-H264HighProfile/Enabled/")
            .createInitializationOptions()
        PeerConnectionFactory.initialize(options)
    }

    private fun createPeerConnectionFactory() :PeerConnectionFactory {
        return PeerConnectionFactory.builder()
            .setVideoDecoderFactory(
                DefaultVideoDecoderFactory(eglBaseContext)
            ).setVideoEncoderFactory(
                DefaultVideoEncoderFactory(
                    eglBaseContext, true, true
                )
            ).setOptions(PeerConnectionFactory.Options().apply {
                disableNetworkMonitor = false
                disableEncryption = false
            }).createPeerConnectionFactory()
    }

    fun initializeWebrtcClient(
        userid: String, observer: PeerConnection.Observer
    ){
        this.userid = userid
        peerConnection = createPeerConnection(observer)
    }

    private fun createPeerConnection(observer: PeerConnection.Observer): PeerConnection? {
        return peerConnectionFactory.createPeerConnection(iceServer, observer)
    }

    interface Listener {
        fun onTransferEventToSocket(data: DataModel)
    }

    private fun initSurfaceView(context: VideoTextureViewRenderer) {

    }

    fun initLocalSurfaceView(localSurfaceView: VideoTextureViewRenderer) {
        this.localSurfaceView = localSurfaceView
        initSurfaceView(localSurfaceView)
    }

    fun getEglBaseContext(): EglBase.Context {
        return eglBaseContext
    }

}