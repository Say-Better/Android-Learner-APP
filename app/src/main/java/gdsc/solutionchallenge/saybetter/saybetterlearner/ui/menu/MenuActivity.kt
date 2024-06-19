package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import android.Manifest
import android.content.Intent
import android.os.Bundle
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
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainServiceRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.ChatBotActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting.SettingActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.VideoCallActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.Customclick.CustomClickEvent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission.checkAndRequestPermissions

import javax.inject.Inject

@AndroidEntryPoint
class MenuActivity: ComponentActivity()  {

    private var userid : String? = null
    private val testUser: String = "testUser1"
    val TAG : String = "ServiceDebug"

    //Hilt 종속성 주입
    @Inject lateinit var mainRepository : MainRepository
    @Inject lateinit var mainServiceRepository : MainServiceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MenuPreview()
        }

        Log.d(TAG, "oncreate")
        init()
    }

    private fun init(){
        userid = intent.getStringExtra("userid")
        if(userid == null) finish()
        //foreground service 시작
        startMyService()
    }

    private fun startMyService() {
        mainServiceRepository.startService(userid!!)
    }

    //Video call 클릭되었을 때
    private fun StartVideoCall(targetUserid : String) {
        mainRepository.sendConnectionRequest(targetUserid) {
            if(it) {
                //videocall 시작해야함
                intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                intent.putExtra("target", targetUserid)
                intent.putExtra("isCaller", true)
                startActivity(intent)
            }
        }
    }


    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun MenuPreview() {

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
            if (areGranted) {
                Log.d("test5", "권한이 동의되었습니다.")
                StartVideoCall(testUser)
            }
            /** 권한 요청시 거부 했을 경우 **/
            else {
                Log.d("test5", "권한이 거부되었습니다.")
            }
        }

        val menuList = listOf(
            menu("레벨 테스트", R.drawable.menu_level),
            menu("그림 상징 의사소통", R.drawable.menu_symbol),
            menu("텍스트 의사소통", R.drawable.menu_text),
            menu("AI 챗봇", R.drawable.menu_chatbot),
            menu("설정", R.drawable.menu_setting),)

        Surface(color = White,
            modifier = Modifier.fillMaxSize()
        ){
            MenuBar(menuList = menuList,
                ClickSymbol = {
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        onPermissionsGranted = {    //권한이 이미 다 있을 때
                            StartVideoCall(testUser)
                        }
                    )
                    // 권환을 받아야할 때
                },
                ClickChatbot = {
                    intent = Intent(this@MenuActivity, ChatBotActivity::class.java)
                    startActivity(intent)
                },
                ClickSetting = {
                    intent = Intent(this@MenuActivity, SettingActivity::class.java)
                    startActivity(intent)
                })
        }
    }

    @Composable
    fun MenuBar(menuList : List<menu>,
                ClickSymbol: () ->Unit,
                ClickChatbot:() ->Unit,
                ClickSetting:() -> Unit) {
        Box {
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(id = R.drawable.ic_say_better),
                    contentDescription = null,
                    modifier = Modifier
                        .height(70.dp)
                        .width(300.dp),)
            }

            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                LazyRow {
                    items(menuList) { menuEntity ->
                        MenuItem(menuEntity, clickMenu = {
                            when (menuEntity.title) {
                                "그림 상징 의사소통" -> {
                                    ClickSymbol()
                                }
                                "AI 챗봇" -> {
                                    ClickChatbot()
                                }
                                "설정" -> {
                                    ClickSetting()
                                }
                                else -> {
                                }
                            }
                        })
                        Log.d("permission", "call FeatureThatRequiresCameraPermission")
                        if (menuEntity != menuList.last()) Spacer(modifier = Modifier.width(30.dp))

                    }
                }
            }
        }

    }

    @Composable
    fun MenuItem(menuEntity : menu, clickMenu: () -> Unit) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable (
                interactionSource = remember{ MutableInteractionSource() },
                indication = CustomClickEvent
            ) {
                clickMenu()
            }){
                Image(
                    painter = painterResource(id = menuEntity.img),
                    contentDescription = null,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .background(Color.White, shape = RoundedCornerShape(50.dp))
                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = menuEntity.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600)
            }
    }
}

