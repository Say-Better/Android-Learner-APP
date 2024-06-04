package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.menu
import gdsc.solutionchallenge.saybetter.saybetterlearner.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.service.MainServiceRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.ChatBotActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.VideoCallActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.FeatureThatRequiresCameraPermission
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

import javax.inject.Inject

@AndroidEntryPoint
class MenuActivity: ComponentActivity()  {

    private var userid : String? = null

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
    @Composable
    private fun StartVideoCall(userid : String) {
        FeatureThatRequiresCameraPermission()
    }


    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun MenuPreview() {
        val menuList = listOf(
            menu("레벨 테스트", R.drawable.menu_level),
            menu("그림 상징 의사소통", R.drawable.menu_symbol),
            menu("텍스트 의사소통", R.drawable.menu_text),
            menu("AI 챗봇", R.drawable.menu_chatbot),
            menu("설정", R.drawable.menu_setting),)

        Surface(color = White,
            modifier = Modifier.fillMaxSize()
        ){
            MenuBar(menuList = menuList)
        }
    }

    @Composable
    fun MenuBar(menuList : List<menu>) {
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
                            val intent : Intent
                            when (menuEntity.title) {
                                "그림 상징 의사소통" -> {
                                    intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                                }
                                "AI 챗봇" -> {
                                    intent = Intent(this@MenuActivity, ChatBotActivity::class.java)
                                }
                                else -> {
                                    intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                                }
                            }
                            startActivity(intent)
                        })
                        FeatureThatRequiresCameraPermission()
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

