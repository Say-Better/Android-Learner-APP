package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.login.LoginActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3초 동안 스플래시 화면 유지
        lifecycleScope.launchWhenCreated {
            delay(3000)
            finish()
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        setContent {
            SplashView()
        }
    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun SplashView(){
    Surface(color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(350.dp))
        }

    }
}