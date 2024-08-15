package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500

@Composable
fun DisplayTexts(mode: Int) {
    val titleList = listOf("로그인에 성공했어요! 시작하기 전 기본 설정이 필요해요.",
        "모든 준비가 완료되었어요! 이제 시작해볼까요?")

    val descriptionList = listOf("프로필 사진은 교육자에게 노출되어 학습자를 구별하는 것에 사용돼요.",
        "마지막으로 원활한 사용을 위해 앱 내 권한을 허용해주세요.")

    Text(
        text = titleList[mode],
        fontSize = 30.sp,
        fontWeight = FontWeight.W600
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = descriptionList[mode],
        fontSize = 20.sp,
        fontWeight = FontWeight.W500,
        color = Gray500
    )
}