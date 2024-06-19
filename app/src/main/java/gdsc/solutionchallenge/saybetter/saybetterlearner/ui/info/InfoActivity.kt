package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.TitleSet
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White


class InfoActivity {
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun InfoPreview() {

    }
    //mode 0,1,2 만들고 3은 end page
    //각 모드에서 끝날때 마다 변수 저장
    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun InfoScreen() {
        var mode by remember { mutableStateOf(0) }

        val titleSet = listOf(TitleSet("로그인에 성공했어요! 시작하기 전 몇 가지 질문을 드릴게요. 이상이 없을 시 다음을 눌러주세요", "학습자의 이름과 성별은 무엇일까요?"),
        TitleSet("좋아요. 그럼 교육자에게 표시될 나의 사진을 골라볼까요?", "프로필 사진을 설정해주세요."),
        TitleSet("로그인에 성공했어요! 시작하기 전 몇 가지 질문을 드릴게요. 이상이 없을 시 다음을 눌러주세요", "학습자의 이름과 성별은 무엇일까요?"),
        )

        Surface {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)){
                Image(painter = painterResource(id = R.drawable.ic_say_better),
                    contentDescription =null,
                    modifier = Modifier
                        .width(250.dp)
                        .height(45.dp))
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = titleSet[mode].title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = titleSet[mode].description,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W600)
            }
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
                Text(text = "시작하기",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600)
            }
        }
    }
    @Composable
    fun InputBtmBar() {
        Row (modifier = Modifier
            .fillMaxWidth()){
            Box (modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
                .background(White)
                .weight(1f),
                contentAlignment = Alignment.Center){
                Text(text = "이전",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600)
            }
            Spacer(modifier = Modifier.width(20.dp))
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



