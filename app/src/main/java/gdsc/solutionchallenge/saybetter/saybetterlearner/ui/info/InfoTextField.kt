package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.clickWithScaleAnimation

@Composable
fun InfoNameTextField(viewModel: InfoViewModel) {

    val name by viewModel.name.collectAsState()

    Column {
        Text(text = "이름",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = name,
            onValueChange = { viewModel.setName(it)},
            trailingIcon = {
                if (name.isNotEmpty()) {
                    IconButton(onClick = {  }) {
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
fun InfoBirthTextField(viewModel: InfoViewModel) {
    val birthday by viewModel.birthDay.collectAsState()

    Column {
        Text(text = "생년월일",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .border(1.dp, Gray500, RoundedCornerShape(20.dp))
                .width(580.dp)
                .height(70.dp)
                .clickWithScaleAnimation(
                    {viewModel.setCustomAlertDialogState(CustomAlertDialogState(code = "open"))}
                ),
            contentAlignment = Alignment.CenterStart
        ){
            Text(text = birthday,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp))
        }
    }
}