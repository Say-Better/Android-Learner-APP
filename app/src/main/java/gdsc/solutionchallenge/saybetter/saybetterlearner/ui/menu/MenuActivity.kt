package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.menu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.ChatBotActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.VideoCallActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent


class MenuActivity: ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuPreview()
        }
    }
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun MenuPreview() {
        val menuList = listOf(
            menu("홈", R.drawable.ic_home),
            menu("챗봇", R.drawable.ic_chatbot),
            menu("솔루션", R.drawable.ic_solution),
            menu("마이페이지", R.drawable.ic_my),)

        Surface(color = MainGreen,
            modifier = Modifier.fillMaxSize()
        ){
            MenuBar(menuList = menuList)
        }
    }

    @Composable
    fun MenuBar(menuList : List<menu>) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            LazyRow {
                items(menuList) { menuEntity ->
                    MenuItem(menuEntity, clickMenu = {
                        val intent : Intent
                        when (menuEntity.title) {
                            "솔루션" -> {
                                intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                            }
                            "챗봇" -> {
                                intent = Intent(this@MenuActivity, ChatBotActivity::class.java)
                            }
                            else -> {
                                intent = Intent(this@MenuActivity, VideoCallActivity::class.java)
                            }
                        }
                        startActivity(intent)
                    })
                    if (menuEntity != menuList.last()) Spacer(modifier = Modifier.width(30.dp))
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
            Box(modifier = Modifier
                .width(270.dp)
                .height(270.dp)
                .background(Color.White, shape = RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = menuEntity.img),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = menuEntity.title,
                fontSize = 25.sp)
        }
    }
}

