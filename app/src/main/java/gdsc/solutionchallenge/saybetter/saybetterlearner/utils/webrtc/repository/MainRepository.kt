package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository

import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.firebaseClient.FirebaseClient
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModelType
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.VideoTextureViewRenderer
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.WebRTCClient
import org.webrtc.EglBase
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient : FirebaseClient,
    private val webRTCClient: WebRTCClient,
    private val gson : Gson
) {
    var listener : Listener? = null

    fun testAccess() {
        firebaseClient.testAccess()
    }

    fun login(userid : String, isDone : (Boolean, String?) -> Unit) {
        firebaseClient.login(userid, isDone)
    }

    fun initFirebase() {
        firebaseClient.subscribeForLatestEvent(object : FirebaseClient.Listener {
            override fun onLatestEventReceived(event: DataModel) {
                listener?.onLatestEventReceived(event)
                when(event.type) {
                    else -> Unit
                }
            }

        })
    }
    fun sendConnectionRequest(target : String, success : (Boolean) -> Unit) {
        firebaseClient.sendMessageToOtherClient(
            DataModel(
                type = DataModelType.StartVideoCall,
                target = target
            ), success
        )
    }

    fun getEglBaseContext(): EglBase.Context {
        return webRTCClient.getEglBaseContext()
    }

    fun initLocalSurfaceView(localSurfaceView: VideoTextureViewRenderer) {
        webRTCClient.initLocalSurfaceView(localSurfaceView)
    }

    interface Listener {
        fun onLatestEventReceived(data : DataModel)
    }

}