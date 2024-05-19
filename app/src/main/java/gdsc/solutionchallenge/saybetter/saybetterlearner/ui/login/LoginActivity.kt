package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: ComponentActivity() {

    @Inject lateinit var mainRepository : MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(login = {
                finish()
                mainRepository.testAccess()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            })
        }
    }

    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun LoginPreview() {
        LoginScreen(login = {
            finish()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        })
    }
    @Composable
    fun LoginScreen(login: () -> Unit) {
        Surface {
            Row (modifier = Modifier.fillMaxSize()){
                Column (modifier = Modifier
                    .padding(start = 90.dp, top = 80.dp, bottom = 80.dp)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()){

                    Text(text = "Say Better Life, Say Better dream",
                        color = MainGreen,
                        fontSize = 25.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "반가워요!\nSay Better를 시작해볼까요?",
                        fontSize = 50.sp)

                    Row (modifier = Modifier
                        .fillMaxSize(),
                        verticalAlignment = Alignment.Bottom){
                        Box (modifier = Modifier
                            .width(500.dp)
                            .height(80.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(100.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = CustomClickEvent
                            ) { login() }
                        ){
                            Row (modifier = Modifier
                                .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically){

                                Spacer(modifier = Modifier.width(40.dp))

                                Image(painter = painterResource(id = R.drawable.google_logo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp))

                                Text(modifier = Modifier.padding(start = 20.dp), text = "Google로 로그인하기",
                                    fontSize = 35.sp)
                            }
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 20.dp, bottom = 80.dp)) {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 100.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom){
                        Image(painter = painterResource(id = R.drawable.rectangle_1631),
                            contentDescription = null,
                            modifier = Modifier
                                .size(250.dp))

                        Spacer(modifier = Modifier.width(40.dp))

                        Image(painter = painterResource(id = R.drawable.rectangle_1632),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp))
                    }

                    Row (modifier = Modifier.padding(top = 100.dp)){
                        Image(painter = painterResource(id = R.drawable.rectangle_1629),
                            contentDescription = null,
                            modifier = Modifier
                                .size(350.dp))
                    }

                    Image(
                        painter = painterResource(id = R.drawable.rectangle_1630),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .align(Alignment.BottomCenter)
                    )
                }

            }
        }

    }

}
