package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.webrtcClient
//
import android.content.Context
import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRTCClient @Inject constructor(
    private val context : Context,
    private val gson : Gson
) {
//    val listener: Listener? = null

    private fun

    interface Listener {
        fun onTransferEventToSocket(data: DataModel)
    }

}