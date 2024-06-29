package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatRoom
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.ChatBotInputLayout.ChatInput
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.NaviBar.NaviMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

class ChatBotActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBatPreview()
        }
    }

    @Preview(widthDp = 1280, heightDp = 800)
    @Composable
    fun ChatBatPreview() {

        val chatMessageList = remember {
            mutableStateListOf(ChatMessage(
                false,
                "19:12",
                "우리 한 번 대화를 시작해볼까? 만나서 반가워~",
                0
            ),   )
        }


        val chatRoomList = listOf(
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol)
        )
        Surface {
            Row(modifier = Modifier.fillMaxSize()) {
                NaviMenu(2)

                ChatList(chatRoomList)

                Column(modifier = Modifier.fillMaxWidth()) {
                    // 채팅 목록
                    LazyColumn(
                        // TopBar 영역과 TextField 영역을 제외한 나머지 공간을 모두 차지하도록
                        modifier = Modifier.fillMaxHeight(0.45f)
                    ) {
                        items(chatMessageList) { chatmessage ->
                            ChatBubble(chatMessage = chatmessage)
                        }
                    }
                    ChatInput(onClickTransmit = {inputText->
                        chatMessageList.add(
                            ChatMessage(
                            isUser = true,
                            timestamp = "20:10",
                            message = inputText,
                            symbol = 0
                        )
                        )
                        Log.d("message", chatMessageList.toString())
                    })
                }
            }

        }
        // 전체 영역을 Column 으로 배치
    }

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
            // ChatRoomList 스크롤 가능한 컬럼
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                item {
                    ChatRoomList(chatRoomList)
                }
            }
            // 새로운 대화 상자 고정
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


    @Composable
    fun ChatBubble(
        chatMessage: ChatMessage,
    ) {
        // 내가 보낸 채팅인지, 아닌지에 따라 Start 또는 End 정렬
        val messageArrangement = if (chatMessage.isUser) Arrangement.End else Arrangement.Start

        // 채팅 아이템의 구성요소들(프로필 사진, 메세지, 시각)을 가로로 배치하기 위함
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(end = 20.dp, top = 20.dp),
            horizontalArrangement = messageArrangement,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 내가 보낸 채팅
            if (chatMessage.isUser) {
                Column (verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End){
                    Image(painter = painterResource(id = R.drawable.ic_speaker_off),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp))
                    Text(text = "다시 듣기",
                        fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                MessageBox(
                    chatMessage
                )
                // 상대방이 보낸 채팅(프로필 사진을 포함)
            } else {
                ProfileImage(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(48.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                MessageBox(
                    chatMessage
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column (verticalArrangement = Arrangement.Center){
                    Image(painter = painterResource(id = R.drawable.ic_speaker_off),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp))
                    Text(text = "다시 듣기",
                        fontSize = 12.sp)
                }
            }
        }
    }

    @Composable
    fun MessageBox(
        chatMessage: ChatMessage
    ) {
        // optional 채팅이 길어질 경우 화면의 최대 2/3 를 차지 하도록
        val maxWidthDp = LocalConfiguration.current.screenWidthDp.dp * 3 / 6

        Box(
            modifier = Modifier
                .widthIn(max = if (chatMessage.isUser) maxWidthDp else maxWidthDp - 56.dp)
                .clip(
                    if (chatMessage.isUser) {
                        RoundedCornerShape(
                            topStart = 15.dp, // Change this as needed
                            topEnd = 0.dp,    // Set to 0.dp for no rounding
                            bottomStart = 15.dp,
                            bottomEnd = 15.dp
                        ) // Change this as needed
                    } else {
                        RoundedCornerShape(
                            topStart = 0.dp, // Change this as needed
                            topEnd = 15.dp,    // Set to 0.dp for no rounding
                            bottomStart = 15.dp,
                            bottomEnd = 15.dp
                        )
                    }
                )
                .background(if (chatMessage.isUser) DarkGray else MainGreen)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (!chatMessage.isUser) {
                Text(
                    text = chatMessage.message,
                    color = White,
                    modifier = Modifier.padding(all = 4.dp)
                )
            } else {
                Text(
                    text = chatMessage.message,
                    color = White,
                    modifier = Modifier.padding(all = 4.dp)
                )
            }
        }
    }

    @Composable
    fun TimeText(time: String) {
        Text(
            text = time,
            color = Gray500,
        )
    }

    @Composable
    fun ProfileImage(
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(SubGreen.copy(alpha = 0.3f), RoundedCornerShape(200.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Draw the image clipped as a circle
            Image(
                painter = painterResource(id = R.drawable.ic_chatbot_profile),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }








