package gdsc.solutionchallenge.saybetter.saybetterlearner.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }
}