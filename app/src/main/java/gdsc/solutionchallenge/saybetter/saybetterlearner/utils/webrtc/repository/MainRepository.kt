package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository

import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.firebaseClient.FirebaseClient
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient.WebRTCClient
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

    interface Listener {
        fun onLatestEventReceived(data : DataModel)
    }

}