package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.input.ChatInput
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray400
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainBlue
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

data class ChatMessage(
    val isUser: Boolean,
    val timestamp:String,
    val message: String,
    val symbol : Int
)

data class ChatMenu(
    val title:String,
    val img : Int
)

data class ChatRoom(
    val title : String,
    val last_date : String,
    val img:Int
)

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

        val chatMessageList = listOf(
            ChatMessage(true, "19:10", "", R.drawable.ic_chatbot),
            ChatMessage(true, "19:11", "", R.drawable.ic_chatbot),
            ChatMessage(
                false,
                "19:12",
                "mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3mess3",
                0
            ),
            ChatMessage(false, "19:13", "mess4", 0),
            ChatMessage(true, "19:14", "", R.drawable.ic_chatbot),
            ChatMessage(false, "19:15", "mess6", 0),
            ChatMessage(true, "19:16", "", R.drawable.ic_chatbot)
        )

        val chatMenuList = listOf(
            ChatMenu("그림", R.drawable.img_chatbot),
            ChatMenu("텍스트", R.drawable.img_chatbot),
            ChatMenu("챗봇", R.drawable.img_chatbot),
            ChatMenu("홈", R.drawable.img_chatbot),
        )

        val chatRoomList = listOf(
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
            ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol)
        )
        Surface {
            Row(modifier = Modifier.fillMaxSize()) {
                ChatMenu(chatMenuList)

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
                    ChatInput()
                }
            }

        }
        // 전체 영역을 Column 으로 배치
    }

    @Composable
    fun ChatMenu(chatMenuList: List<ChatMenu>) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.05f)
                .fillMaxHeight()
                .background(Black),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.42f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(chatMenuList) { chatmenu ->
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_chatbot),
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
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                drawLine(
                    color = Gray400,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 3f
                )
            }
            // ChatRoomList 스크롤 가능한 컬럼
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                item {
                    ChatRoomList(0, chatRoomList)
                    ChatRoomList(1, chatRoomList)
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
    fun ChatRoomList(mode: Int, chatRoomList: List<ChatRoom>) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (mode == 0) "진행중 학습"
                    else "완료된 학습",
                    color = Gray500,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_down_arrow),
                    contentDescription = null
                )
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
            verticalAlignment = Alignment.Top,
        ) {
            // 내가 보낸 채팅
            if (chatMessage.isUser) {
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
                .clip(RoundedCornerShape(8.dp))
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
                Image(
                    painter = painterResource(id = chatMessage.symbol), // 여기에 프로필 이미지 리소스 아이디를 넣어주세요.
                    contentDescription = "Symbols",
                    modifier = Modifier.padding(10.dp)
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
                painter = painterResource(id = R.drawable.ic_chatbot),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }





}


