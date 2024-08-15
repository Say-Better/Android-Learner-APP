package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatRoom
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

@Composable
fun ChatList(chatRoomList: List<ChatRoom>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .background(Gray200)
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "채팅방 목록",
                fontSize = 23.sp,
                fontWeight = FontWeight.W600
            )
            Image(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = null
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                ChatRoomList(chatRoomList)
            }
        }

        Box(
            modifier = Modifier
                .background(MainGreen, RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "새로운 대화 시작하기",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun ChatRoomList(chatRoomList: List<ChatRoom>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
    }
    chatRoomList.forEach { chatRoom ->
        ChatRoom(chatRoom)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun ChatRoom(chatRoom: ChatRoom) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = chatRoom.img),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Column {
                Text(
                    text = chatRoom.title,
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = chatRoom.last_date,
                    fontSize = 10.sp,
                    color = Gray500
                )
            }
        }
    }
}