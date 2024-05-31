package gdsc.solutionchallenge.saybetter.saybetterlearner.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.service.MainServiceActions.START_SERVICE
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.DataModel
import javax.inject.Inject


@AndroidEntryPoint
class MainService : Service(), MainRepository.Listener {
    val TAG : String = "MainService"

    private var isServiceRunning = false
    private var userid : String? = null

    private lateinit var notificationManager : NotificationManager

    @Inject lateinit var mainRepository : MainRepository

    //생성되면 NotificationManager 가져오기
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(
            NotificationManager::class.java
        )
    }

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
        //Service 시작 시점에서 toggle on
        if(!isServiceRunning) {
            isServiceRunning = true
            userid = incomingIntent.getStringExtra("userid")
            startServiceWithNotification()

            //setup my clients
            mainRepository.listener = this
            mainRepository.initFirebase()
        }
    }

    //Notification Manager에게 정의한 notificationChannel 전달하여 생성하기
    private fun startServiceWithNotification() {
        val notificationChannel = NotificationChannel(
            "channel1", "foreground", NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(
            this, "channel1"
        )

        startForeground(1, notification.build())
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }

    override fun onLatestEventReceived(data: DataModel) {
        Log.d(TAG, "onLatestEventReceived: $data")
    }


}