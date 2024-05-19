package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey


@Composable
fun InputSymbol(modifier: Modifier){
    Row (modifier = modifier
        .fillMaxWidth(0.83f)
        .fillMaxHeight()){
        Box (modifier = modifier
            .background(SubGrey, RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
            .fillMaxHeight(),
            contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                modifier = modifier.size(28.dp))
        }
        Box (modifier = modifier
            .background(DarkGray)
            .fillMaxHeight()
            .weight(1f)){
            //Symbol layout
        }
        Box (modifier = modifier
            .background(SubGrey, RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
            .fillMaxHeight(),
            contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                contentDescription = null,
                modifier = modifier.size(28.dp))
        }
    }
}