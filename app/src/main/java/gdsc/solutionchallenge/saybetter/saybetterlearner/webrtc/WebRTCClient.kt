package gdsc.solutionchallenge.saybetter.saybetterlearner.webrtc

import android.content.Context
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRTCClient @Inject constructor(
    private val context : Context,
    private val gson : Gson
) {
}