package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

@Composable
fun FinishBtmBar(onClickFinish:() ->Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()){
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
            .background(White)
            .weight(1f)
            .clickable {
                onClickFinish()
            },
            contentAlignment = Alignment.Center){
            Text(text = "다음",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600)
        }
    }
}

@Composable
fun LoginFinishBtmBar(onClickFinish:() ->Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()){
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MainGreen, RoundedCornerShape(100.dp))
            .weight(1f)
            .clickable {
                onClickFinish()
            },
            contentAlignment = Alignment.Center){
            Text(text = "시작하기",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600,
                color = White
            )
        }
    }
}