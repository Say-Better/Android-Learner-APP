package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.menu
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.ChatBotActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.TestDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info.InfoActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting.SettingActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.VideoCallActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.BackOnPressed
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission.checkAndRequestPermissions
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService

import javax.inject.Inject

@AndroidEntryPoint
class MenuActivity: ComponentActivity() , MainService.CallEventListener {

    data class TestDialogState(
        val isClick : Boolean = false,
        val onClickSure: () -> Unit = {},
        val onClickCancel: () -> Unit = {},
    )

    private var userId: String? = null
    private val testUser: String = "testUser1"
//    private var currentReceivedModel: DataModel? = null
    private var receivedModelState: MutableState<DataModel?> = mutableStateOf(null)
    private val customAlertDialogState = mutableStateOf(TestDialogState())

    //Hilt 종속성 주입
    @Inject lateinit var mainRepository : MainRepository
    @Inject lateinit var mainServiceRepository : MainServiceRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        setContent {
            BackOnPressed {
                mainServiceRepository.stopService()
            }
            MenuView(
                resetDialogState = {resetDialogState(customAlertDialogState)},
                startVideoCall = {targetUser, isCaller -> startVideoCall(targetUser, isCaller) },
                targetUser = userId!!,
                currentReceivedModel = receivedModelState,
                customAlertDialogState = customAlertDialogState,
                onClickChatbot = {
                    intent = Intent(this@MenuActivity, ChatBotActivity::class.java)
                    startActivity(intent)
                },
                onClickSetting = {
                        intent = Intent(this@MenuActivity, SettingActivity::class.java)
                        startActivity(intent)
                                 },
                onClickLevel = {
                    intent = Intent(this@MenuActivity, InfoActivity::class.java)
                    startActivity(intent)
                })
        }
    }

    // 화상통화가 종료되었을 때 dialog가 뜨는 것을 방지
    override fun onResume() {
        super.onResume()
        resetDialogState(customAlertDialogState)
    }

    private fun init(){
        userId = intent.getStringExtra("userid")
        if(userId == null) finish()

        MainService.listener = this
        startMyService()
    }

    private fun startMyService() {
        mainServiceRepository.startService(userId!!)
    }

    //Video call 클릭되었을 때
    private fun startVideoCall(targetUserid : String, isCaller: Boolean) {
        mainRepository.sendConnectionRequest(targetUserid) {
            if(it) {
                //videocall 시작해야함
                //educator 되면 수정
                intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                intent.putExtra("target", targetUserid)
                intent.putExtra("isCaller", isCaller)
                startActivity(intent)
            }
        }
    }
    override fun onCallReceived(model: DataModel) {
        Log.d("MainService", "call receive by ${model.sender}")
        receivedModelState.value = model

        customAlertDialogState.value = TestDialogState(
            isClick = true,
            onClickCancel = {},
            onClickSure = {}
        )
    }

    fun resetDialogState(state: MutableState<TestDialogState>) {
        state.value = TestDialogState()
    }
}


