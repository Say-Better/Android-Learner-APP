package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.menu
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.ChatBotActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.TestDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info.InfoActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting.SettingActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission.checkAndRequestPermissions

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun MenuPreview() {
    val mockDialogState = remember { mutableStateOf(MenuActivity.TestDialogState()) }
    val receivedModelState: MutableState<DataModel?> = remember{ mutableStateOf(null) }

    MenuView(
        resetDialogState = { mockDialogState.value = MenuActivity.TestDialogState() },
        startVideoCall = { _, _ -> },
        targetUser = "testUser1",
        currentReceivedModel = receivedModelState,
        customAlertDialogState = mockDialogState,
        onClickChatbot = { /* Handle chatbot click */ },
        onClickSetting = { /* Handle setting click */ },
        onClickLevel = { /* Handle level test click */ }
    )
}

@Composable
fun MenuView(
    resetDialogState: () -> Unit,
    startVideoCall: (String, Boolean) -> Unit,
    targetUser: String,
    currentReceivedModel : MutableState<DataModel?>,
    customAlertDialogState: MutableState<MenuActivity.TestDialogState>,
    onClickChatbot: () -> Unit,
    onClickSetting: () -> Unit,
    onClickLevel: () -> Unit
) {
    var isCaller: Boolean? = null

    val context = LocalContext.current

    /** 요청할 권한 **/
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        /** 권한 요청시 동의 했을 경우 **/
        /** 권한 요청시 동의 했을 경우 **/
        if (areGranted) {
            Log.d("test5", "권한이 동의되었습니다.")
            Log.d("test5", currentReceivedModel.value?.sender!!)
            resetDialogState()
            startVideoCall(currentReceivedModel.value?.sender!!, isCaller!!)
        }
        /** 권한 요청시 거부 했을 경우 **/
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("test5", "권한이 거부되었습니다.")
        }
    }

    val menuList = listOf(
        MenuItemModel("레벨 테스트", R.drawable.menu_level, onClick = { /* Navigate to Level Test */ }),
        MenuItemModel("그림 상징 의사소통", R.drawable.menu_symbol, onClick = { /* Start Video Call */ }),
        MenuItemModel("텍스트 의사소통", R.drawable.menu_text, onClick = { /* Navigate to Text Communication */ }),
        MenuItemModel("AI 챗봇", R.drawable.menu_chatbot, onClick = { /* Navigate to Chatbot */ }),
        MenuItemModel("설정", R.drawable.menu_setting, onClick = { /* Navigate to Settings */ })
    )

    Surface(color = White,
        modifier = Modifier.fillMaxSize()
    ){
        if (customAlertDialogState.value.isClick) {
            TestDialog().CallTestDialog(
                onClickSure = {
                    isCaller = false
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        onPermissionsGranted = {    //권한이 이미 다 있을 때
                            Log.d("MenuViewState", currentReceivedModel.toString())
                            resetDialogState()
                            startVideoCall(currentReceivedModel.value?.sender!!, isCaller!!)
                        }
                    )
                },
                onClickCancel = { resetDialogState() }
            )
        }

        MenuBar(
            menuList = menuList,
            onClickMenuItem = { menuItem ->
                when (menuItem.title) {
                    "레벨 테스트" -> {
                        onClickLevel()
                    }
                    "그림 상징 의사소통" -> {
                        isCaller = true
                        checkAndRequestPermissions(
                            context,
                            permissions,
                            launcherMultiplePermissions,
                            onPermissionsGranted = {    //권한이 이미 다 있을 때
                                startVideoCall("helloYI", isCaller!!) // 타겟이 들어가야함
                            }
                        )
                    }
                    "설정" -> {
                        onClickSetting()
                    }
                    "AI 챗봇" -> {
                        onClickChatbot()
                    }
                    // Handle other menu clicks similarly
                }
            }
        )
    }
}