package gdsc.solutionchallenge.saybetter.saybetterlearner.repository

import gdsc.solutionchallenge.saybetter.saybetterlearner.firebaseClient.FirebaseClient
import gdsc.solutionchallenge.saybetter.saybetterlearner.webrtc.WebRTCClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient : FirebaseClient,
    private val webRTCClient: WebRTCClient
) {

    fun testAccess() {
        firebaseClient.testAccess()
    }

}