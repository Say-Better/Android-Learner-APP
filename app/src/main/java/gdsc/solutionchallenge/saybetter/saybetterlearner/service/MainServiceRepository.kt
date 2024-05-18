package gdsc.solutionchallenge.saybetter.saybetterlearner.service

import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject


class MainServiceRepository @Inject constructor(
    private val context : Context
) {
    fun startService(username : String) {
        Thread{
            val intent = Intent(context, MainService::class.java)
            intent.putExtra("username", username)
            intent.action = MainServiceActions.START_SERVICE.name
            startServiceIntent(intent)
        }.start()
    }

    private fun startServiceIntent(intent : Intent) {
        //Foreground service: 시스템에 의해 종료될 확률이 적음
        context.startForegroundService(intent)
    }

}