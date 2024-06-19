package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.repository.MainRepository
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.Customclick.CustomClickEvent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.GoogleSignInHelper
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: ComponentActivity() {

    @Inject lateinit var mainRepository : MainRepository


    private lateinit var googleSignInHelper: GoogleSignInHelper

    val testid : String = "helloYI"

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        googleSignInHelper = GoogleSignInHelper(this) {googleIdToken ->
            if(googleIdToken !=null) {
                finish()
                startActivity(Intent(this@LoginActivity, MenuActivity::class.java).apply {
                    putExtra("userid", testid)
                    putExtra("googleToken", googleIdToken)
                })
            }
        }

        setContent {
            LoginScreen(login = {
                mainRepository.login(testid) { isDone, reason ->
                    if (!isDone) {
                        Log.d("login", "로그인 실패, $reason")
                    } else {
                        Log.d("login", "로그인 성공")
                        googleSignInHelper.login()
                    }
                }
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
            Box {
                Column (modifier = Modifier){
                    Row (modifier = Modifier.fillMaxSize()){
                        Column (modifier = Modifier
                            .fillMaxWidth(0.45f)
                            .fillMaxHeight()
                            .padding(start = 50.dp, top = 80.dp, end = 50.dp)){

                            Text(text = "Say Better Life, Say Better dream",
                                color = MainGreen,
                                fontSize = 25.sp)

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "반가워요!\nSay Better를 시작해볼까요?",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.W800)
                        }
                        Image(painter = painterResource(id = R.drawable.img_login),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize())
                    }
                }
                Row (modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp, vertical = 50.dp),
                    verticalAlignment = Alignment.Bottom){
                    Box (modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
                        .background(White)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = CustomClickEvent
                        ) { login() }
                    ){
                        Row (modifier = Modifier
                            .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){

                            Spacer(modifier = Modifier.width(40.dp))

                            Image(painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp))

                            Text(modifier = Modifier.padding(start = 20.dp), text = "Google로 로그인하기",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.W600)
                        }
                    }
                }
            }
        }
    }

}
