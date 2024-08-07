package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.navibar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White


val chatMenuList = listOf(
    ChatMenu("그림 상징", R.drawable.menu_symbol, R.drawable.menu_symbol_off),
    ChatMenu("텍스트", R.drawable.menu_text, R.drawable.menu_text_off),
    ChatMenu("챗봇", R.drawable.menu_chatbot, R.drawable.menu_symbol_off),
    ChatMenu("설정", R.drawable.menu_setting, R.drawable.menu_setting_off),
)
@Composable
fun NaviMenu(mode : Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.05f)
            .fillMaxHeight()
            .background(DarkGray),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(chatMenuList) { index, chatmenu ->
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = if (mode == index) chatmenu.img
                        else chatmenu.img_off),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(7.dp)
                    )
                    Text(
                        text = chatmenu.title,
                        fontSize = 10.sp,
                        color = White
                    )
                }

            }
        }
    }
}