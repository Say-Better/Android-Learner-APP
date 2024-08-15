package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.menu
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

@Composable
fun MenuItem(menuEntity : MenuItemModel, onClickMenu: () -> Unit) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable (
            interactionSource = remember{ MutableInteractionSource() },
            indication = CustomClickEvent
        ) {
            onClickMenu()
        }){
        Image(
            painter = painterResource(id = menuEntity.iconRes),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .background(Color.White, shape = RoundedCornerShape(50.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = menuEntity.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W600)
    }
}