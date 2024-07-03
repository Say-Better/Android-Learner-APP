package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import javax.inject.Inject


class MainServiceRepository @Inject constructor(
    private val context : Context
) {
    val TAG : String = "ServiceDebug"

    fun startService(userid : String) {
        Thread{
            val intent = Intent(context, MainService::class.java)
            intent.putExtra("userid", userid)
            intent.action = MainServiceActions.START_SERVICE.name
            startServiceIntent(intent)
        }.start()
    }

    fun setupViews(caller: Boolean, target: String) {
        val intent = Intent(context, MainService::class.java)
        intent.apply {
            action = MainServiceActions.SETUP_VIEWS.name
            putExtra("target", target)
            putExtra("isCaller", caller)
        }
        startServiceIntent(intent)
    }

    private fun startServiceIntent(intent : Intent) {
        //Foreground service: 시스템에 의해 종료될 확률이 적음
        context.startForegroundService(intent)
    }

    fun sendEndCall() {
        val intent = Intent(context, MainService::class.java)
        intent.action = MainServiceActions.END_CALL.name
        startServiceIntent(intent)
    }

}