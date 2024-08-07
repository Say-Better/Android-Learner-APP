package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Red
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

class TestDialog {

    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun DialogPreview() {
        CallTestDialog({  }, {  })
    }
    @Composable
    fun CallTestDialog(
        onClickSure:() -> Unit,
        onClickCancel: () -> Unit
    ) {
        Dialog(
            onDismissRequest = { onClickCancel() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(300.dp)
                    .background(White, RoundedCornerShape(20.dp))// Card의 모든 꼭지점에 8.dp의 둥근 모서리 적용
            )
            {
                Column (
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "학습 진행 test",
                            fontSize = 20.sp,
                            color = Black
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .background(Red, RoundedCornerShape(8.dp))
                            .height(60.dp)
                            .weight(1f)
                            .clickable {
                                onClickCancel()
                            },
                            contentAlignment = Alignment.Center) {
                            Text(text = "거절",
                                color= White)
                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .background(MainGreen, RoundedCornerShape(8.dp))
                            .height(60.dp)
                            .weight(1f)
                            .clickable {
                                onClickSure()
                            },
                            contentAlignment = Alignment.Center) {
                            Text(text = "수락")
                        }
                    }

                }

            }
        }
    }
}