package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

@Composable
fun InfoGender(
    viewModel: InfoViewModel,
    clickMale:()->Unit,
    clickFemale:() ->Unit) {

    val gender by viewModel.gender.collectAsState()

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