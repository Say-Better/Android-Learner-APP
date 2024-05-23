package gdsc.solutionchallenge.saybetter.saybetterlearner.service

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
            Log.d(TAG, "thread start")
            val intent = Intent(context, MainService::class.java)
            intent.putExtra("userid", userid)
            intent.action = MainServiceActions.START_SERVICE.name
            startServiceIntent(intent)
            Log.d(TAG, "thread end")
        }.start()
    }

    private fun startServiceIntent(intent : Intent) {
        //Foreground service: 시스템에 의해 종료될 확률이 적음
        Log.d(TAG, "startServiceIntent")
        context.startForegroundService(intent)
    }

}