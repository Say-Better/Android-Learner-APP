package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.Dialog.LearnerCodeDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.NaviBar.NaviMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainColor
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import java.nio.file.WatchEvent

class SettingActivity: ComponentActivity()  {

    data class CustomAlertDialogState(
        val code: String = "",
        val onClickCancel: () -> Unit = {},
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val customAlertDialogState = remember { mutableStateOf(CustomAlertDialogState()) }
            Surface {
                Row(Modifier.fillMaxSize()) {
                    NaviMenu(mode = 3)
                    SettingScreen(customAlertDialogState)
                }
                if (customAlertDialogState.value.code.isNotEmpty()) {
                    LearnerCodeDialog().LearnerCodeDialogScreen(
                        code = customAlertDialogState.value.code,
                        onClickCancel = { resetDialogState(customAlertDialogState) }
                    )
                }
            }
        }
    }


    private fun resetDialogState(state: MutableState<CustomAlertDialogState>) {
        state.value = CustomAlertDialogState()
    }

    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun SettingPreview() {
        Surface {
            Row(Modifier.fillMaxSize()) {
                NaviMenu(mode = 3)
                //SettingScreen()
            }
        }
    }
    @Composable
    fun SettingScreen(customAlertDialogState: MutableState<CustomAlertDialogState>) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)){
            Text(text = "프로필",
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,)
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(BoxBackground, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)) {
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.drawable.setting_dummy_img),
                        contentDescription = null,
                        Modifier.size(140.dp))
                    Column (modifier = Modifier.padding(10.dp)){
                        Text(text = "이름",
                            color = SubGrey,
                            fontSize = 14.sp)

                        Text(text = "홍길동",
                            color = Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 19.sp)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "나이/성별",
                            color = SubGrey,
                            fontSize = 14.sp)

                        Text(text = "16세/남자",
                            color = Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 19.sp)
                    }
                }

                Box(modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .background(White, shape = RoundedCornerShape(30.dp))
                    .height(40.dp)
                    .width(160.dp)
                    .border(1.dp, SubGrey, RoundedCornerShape(30.dp))) {
                    Row (modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center){
                        Image(painter = painterResource(id = R.drawable.icon_settings),
                            contentDescription = null)

                        Text(text = "프로필 설정",
                            fontSize = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "학습자 등록 코드 발급",
                fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "학습자 등록 코드는 교육자가 학습자를 등록하기 위해 꼭 필요해요! 코드는 발급 시 n분간 사용이 가능합니다.",
                color = SubGrey)

            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .background(MainGreen, shape = RoundedCornerShape(30.dp))
                .height(50.dp)
                .width(120.dp)
                .clickable {
                    customAlertDialogState.value = CustomAlertDialogState(
                        code = "NTR139",
                        onClickCancel = {
                            resetDialogState(customAlertDialogState)
                        }
                    )
                }) {
                Row (modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Text(text = "발급받기",
                        fontSize = 20.sp,
                        color = White)
                }
            }
        }
    }
}