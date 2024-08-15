package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGetResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.navibar.NaviMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

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
fun SettingView(
    memberInfo : MutableState<MemberGetResponse>,
    onClickGenCode:() -> Unit) {

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
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(16.dp)) // 원하는 둥근 정도로 설정
                ) {
                    Image(painter = rememberAsyncImagePainter(memberInfo.value.imgUrl),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize())
                }
                Column (modifier = Modifier.padding(10.dp)){
                    Text(text = "이름",
                        color = SubGrey,
                        fontSize = 14.sp)

                    Text(text = memberInfo.value.name,
                        color = Black,
                        fontWeight = FontWeight.W700,
                        fontSize = 19.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "나이/성별",
                        color = SubGrey,
                        fontSize = 14.sp)

                    Text(text = "${memberInfo.value.age} 세/" + if(memberInfo.value.gender == "MALE") "남자" else "여성",
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
        Text(text = "학습자 등록 코드는 교육자가 학습자를 등록하기 위해 꼭 필요해요! 코드는 발급 시 3분간 사용이 가능합니다.",
            color = SubGrey
        )

        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .background(MainGreen, shape = RoundedCornerShape(30.dp))
            .height(50.dp)
            .width(120.dp)
            .clickable {
                onClickGenCode()
            }) {
            Row (modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(text = "발급받기",
                    fontSize = 20.sp,
                    color = White
                )
            }
        }
    }
}