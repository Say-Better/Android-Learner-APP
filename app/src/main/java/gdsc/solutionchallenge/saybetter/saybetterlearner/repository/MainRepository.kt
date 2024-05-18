package gdsc.solutionchallenge.saybetter.saybetterlearner.repository

import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.firebaseClient.FirebaseClient
import gdsc.solutionchallenge.saybetter.saybetterlearner.webrtc.WebRTCClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient : FirebaseClient,
    private val webRTCClient: WebRTCClient,
    private val gson : Gson
) {

    fun testAccess() {
        firebaseClient.testAccess()
    }

}