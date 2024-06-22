package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.Dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black

class InfoProfileDialog {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheet(
        onClickDismiss:() -> Unit,
        onClickCamera: () -> Unit,
        onClickGallary:() -> Unit
    ) {

        ModalBottomSheet(onDismissRequest = {
            onClickDismiss()
        }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 30.dp)
            ) {
                Text(text = "프로필 설정")
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "카메라 촬영",
                    fontSize = 25.sp,
                    color = Black,
                    modifier = Modifier
                        .clickable {
                            onClickCamera()
                        })
                Canvas(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(bottom = 20.dp, top = 20.dp)
                ) {
                    drawLine(
                        color = Black,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1f
                    )
                }
                Text(text = "갤러리에서 선택",
                    fontSize = 25.sp,
                    color = Black,
                    modifier = Modifier
                        .clickable {
                            onClickGallary()
                        })
            }
        }



    }
}