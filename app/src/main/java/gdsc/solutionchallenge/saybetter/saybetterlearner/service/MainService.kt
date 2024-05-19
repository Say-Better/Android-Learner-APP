package gdsc.solutionchallenge.saybetter.saybetterlearner.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.service.MainServiceActions.START_SERVICE


@AndroidEntryPoint
class MainService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { incomingIntent ->
            when(incomingIntent.action) {
                START_SERVICE.name -> handleStartService(incomingIntent)
                else -> Unit
            }
        }
        return START_STICKY
    }

    private fun handleStartService(incomingIntent: Intent) {
        TODO("Not yet implemented")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }
}