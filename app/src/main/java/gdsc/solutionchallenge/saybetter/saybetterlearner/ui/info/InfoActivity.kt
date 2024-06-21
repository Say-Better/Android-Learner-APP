package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.HighlightBorder
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.GoogleSignInHelper


class InfoActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InfoScreen()
        }
    }
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun InfoPreview() {

    }
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun InfoScreen() {
        var mode by remember { mutableStateOf(0) }
        var name : MutableState<String> = remember {
            mutableStateOf("")}
        var birthday : MutableState<String> = remember {
            mutableStateOf("")}
        var gender by remember {
            mutableStateOf(false)
        }

        Surface {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)){

                Text(text = "로그인에 성공했어요! 시작하기 전 기본 설정이 필요해요.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W600)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "프로필 사진은 교육자에게 노출되어 학습자를 구별하는 것에 사용돼요.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Gray500)
                Spacer(modifier = Modifier.height(60.dp))
                Row {
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Image(painter = painterResource(id = R.drawable.info_img),
                            contentDescription = null,
                            modifier = Modifier.size(320.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Box (modifier = Modifier
                            .background(BoxBackground, RoundedCornerShape(50.dp))
                            .width(320.dp)
                            .height(60.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Image(painter = painterResource(id = R.drawable.ic_camera_info),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = "프로필 설정",
                                    fontSize = 20.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "기본 이미지 사용")
                    }
                    Spacer(modifier = Modifier.width(60.dp))
                    Column {
                        InfoTextField(title = "이름", name)
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoTextField(title = "생년월일", birthday)
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoGender(gender,
                            clickMale = {gender = false},
                            clickFemale = {gender = true})
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
                FinishBtmBar()
            }
        }
    }
    @Composable
    fun InfoGender(
        gender : Boolean,
        clickMale:()->Unit,
        clickFemale:() ->Unit) {
        Column {
            Text(text = "성별",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.W500)
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.width(580.dp)){
                Box (modifier = Modifier
                    .background(
                        if (!gender) MainGreen
                        else BoxBackground,
                        RoundedCornerShape(20.dp)
                    )
                    .weight(1f)
                    .height(70.dp)
                    .clickable {
                        clickMale()
                    },
                    contentAlignment = Alignment.Center

                ){
                    Text(text = "남성",
                        fontSize = 20.sp,
                        color = if(!gender) White
                    else SubGrey,
                        fontWeight = FontWeight.W600)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box (modifier = Modifier
                    .background(
                        if (gender) MainGreen
                        else BoxBackground,
                        RoundedCornerShape(20.dp)
                    )
                    .weight(1f)
                    .height(70.dp)
                    .clickable {
                        clickFemale()
                    },
                    contentAlignment = Alignment.Center

                ){
                    Text(text = "여성",
                        fontSize = 20.sp,
                        color = if(gender) White
                        else SubGrey,
                        fontWeight = FontWeight.W600)
                }
            }
        }
    }
    @Composable
    fun InfoTextField(title : String, content: MutableState<String>) {

        Column {
            Text(text = title,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.W500)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = content.value,
                onValueChange = {content.value = it},
                trailingIcon = {
                    if (content.value.isNotEmpty()) {
                        IconButton(onClick = { content.value = "" }) {
                            Image(painter = painterResource(id = R.drawable.ic_textfield_delete),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp))
                        }
                    }
                },
                modifier = Modifier
                    .border(1.dp, Gray500, RoundedCornerShape(20.dp))
                    .width(580.dp)
                    .height(70.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = White, // 배경색 설정
                    focusedIndicatorColor = Color.Transparent, // 포커스된 상태의 밑줄 제거
                    unfocusedIndicatorColor = Color.Transparent, // 비포커스 상태의 밑줄 제거
                    disabledIndicatorColor = Color.Transparent // 비활성화 상태의 밑줄 제거
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp, // 텍스트 크기 설정
                    lineHeight = 30.sp,
                ),
                singleLine = true, // 단일 줄로 설정
                maxLines = 1, // 최대 줄 수 설정
            )
        }
    }

    @Composable
    fun FinishBtmBar() {
        Row (modifier = Modifier
            .fillMaxWidth()){
            Box (modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
                .background(White)
                .weight(1f),
                contentAlignment = Alignment.Center){
                Text(text = "다음",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600)
            }
        }
    }

}



