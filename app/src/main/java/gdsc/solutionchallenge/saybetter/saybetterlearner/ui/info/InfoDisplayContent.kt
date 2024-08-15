package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel

@Composable
fun DisplayContent(
    viewModel: InfoViewModel,
    mode: Int,
    context: Context) {
    Row {
        if (mode == 0) {
            InfoImageView(viewModel,context)
            Spacer(modifier = Modifier.width(40.dp))
            Column {
                InfoNameTextField(viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                InfoBirthTextField(viewModel)
                Spacer(modifier = Modifier.height(20.dp))
                InfoGender(viewModel,
                    clickMale = { viewModel.setGender(false) },
                    clickFemale = { viewModel.setGender(true) })
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.img_login_finish),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(430.dp)
            )
        }
    }
}