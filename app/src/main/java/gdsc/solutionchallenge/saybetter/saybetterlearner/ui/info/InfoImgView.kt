package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.GetImageUri

@Composable
fun InfoImageView(viewModel: InfoViewModel, context: Context) {
    val profileImageUri by viewModel.imageUri.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .size(320.dp)
            .clip(RoundedCornerShape(16.dp))) {
            AsyncImage(
                model = profileImageUri,
                contentDescription = null,
                modifier = Modifier.size(320.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(BoxBackground, RoundedCornerShape(50.dp))
                .width(320.dp)
                .height(60.dp)
                .clickable {
                    viewModel.setShowBottomSheet(true)
                },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera_info),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "프로필 설정",
                    fontSize = 20.sp
                )
            }
        }
        Text(text = "기본 이미지 사용",
            modifier = Modifier
                .clickable { viewModel.setImageUri(GetImageUri().getDrawableUri(context, R.drawable.info_img)!! )})
    }
}