package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatRoom
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.sqlite.ChatDatabaseHelper
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.chat.ChatResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service.ChatService
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.chat.ChatBotView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.ChatBotViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.chatbotinputlayout.ChatInput
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.navibar.NaviMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainBlue
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import kotlinx.coroutines.launch

class ChatBotActivity: ComponentActivity(), ChatBotView {

    private lateinit var chatBotViewModel: ChatBotViewModel
    private lateinit var chatService: ChatService
    private lateinit var chatDB:ChatDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatBotViewModel = ChatBotViewModel(context = this)

        chatService = ChatService()
        chatService.setChatBotView(this@ChatBotActivity)

        setContent {
            ChatBatPreview(
                chatBotViewModel,
                chatService,
                getSharedPreferences("Member", Context.MODE_PRIVATE).getString("Jwt", "")!!)
        }
    }

    override fun onGetChatSuccess(response: GeneralResponse<ChatResponse>) {
        chatBotViewModel.addMessage(ChatMessage(false, response.result!!.firstAnswer, 0))
    }

    override fun onGetChatFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("getChat", "fail")
    }
}

@Composable
fun ChatBatPreview(
    chatBotViewModel : ChatBotViewModel,
    chatService: ChatService,
    accessToken:String) {

    val chatMessageList = chatBotViewModel.chatMessageList.collectAsState(initial = emptyList())
    val selectedMessageIndex = chatBotViewModel.selectedMessageIndex.collectAsState()
    val highlightingRange = chatBotViewModel.highlightedRange.collectAsState()
    val isSpeaking = chatBotViewModel.isSpeaking.collectAsState()


    val chatRoomList = listOf(
        ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
        ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
        ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
        ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol),
        ChatRoom("학교에 등교하기 위해 밥 먹고 가는 상황입니다", "최근 방문 1일전", R.drawable.symbol)
    )
    Surface {
        Row(modifier = Modifier.fillMaxSize()) {
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            NaviMenu(2)

            ChatList(chatRoomList)

            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.45f),
                    state = lazyListState
                ) {
                    itemsIndexed(chatMessageList.value) { index, chatmessage ->
                        ChatBubble(
                            chatMessage = chatmessage,
                            isSelected = if (index == selectedMessageIndex.value) true
                            else false,
                            highlightingIndex = highlightingRange.value,
                            isSpeaking = isSpeaking.value,
                            onClickReListen = {
                                chatBotViewModel.selectMessage(index)
                                chatBotViewModel.speak(chatmessage.message)
                            }
                        )
                    }
                }
                LaunchedEffect(chatMessageList.value.size) {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(chatMessageList.value.size - 1)
                    }
                }
                ChatInput(onClickTransmit = { inputText ->
                    chatBotViewModel.addMessage(
                        ChatMessage(
                            isUser = true,
                            message = inputText,
                            symbol = 0
                        )
                    )
                    Log.d("message", chatMessageList.toString())
                    chatService.getChatBot(accessToken, inputText)
                })
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


@Composable
fun ChatBubble(
    chatMessage: ChatMessage,
    isSelected : Boolean,
    highlightingIndex : Pair<Int,Int>,
    isSpeaking : Boolean,
    onClickReListen:() -> Unit
) {
    val messageArrangement = if (chatMessage.isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 20.dp, top = 10.dp),
        horizontalArrangement = messageArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (chatMessage.isUser) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(id = if(isSpeaking && isSelected) R.drawable.ic_speaker_on
                    else R.drawable.ic_speaker_off),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                if ((!isSpeaking && isSelected) || !isSelected) {
                    Text(
                        text = "다시 듣기",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {
                            onClickReListen()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            MessageBox(
                chatMessage,
                highlightingIndex,
                isSelected
            )
        } else {
            ProfileImage(
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(48.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            MessageBox(
                chatMessage,
                highlightingIndex,
                isSelected
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = if(isSpeaking && isSelected) R.drawable.ic_speaker_on
                    else R.drawable.ic_speaker_off),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                if ((!isSpeaking && isSelected) || !isSelected) {
                    Text(
                        text = "다시 듣기",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {
                            onClickReListen()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBox(
    chatMessage: ChatMessage,
    highlightingIndex : Pair<Int,Int>,
    isSelected : Boolean
) {
    val maxWidthDp = LocalConfiguration.current.screenWidthDp.dp * 3 / 6

    Box(
        modifier = Modifier
            .widthIn(max = if (chatMessage.isUser) maxWidthDp else maxWidthDp - 56.dp)
            .clip(
                if (chatMessage.isUser) {
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 0.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                } else {
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                }
            )
            .background(if (chatMessage.isUser) DarkGray else MainGreen)
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {

        if (isSelected) {
            val annotatedString = buildAnnotatedString {
                val text = chatMessage.message
                append(text)
                addStyle(
                    style = SpanStyle(background = MainBlue),
                    start = highlightingIndex.first,
                    end = highlightingIndex.second
                )
            }
            Text(
                text = annotatedString,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }else {
            Text(
                text = chatMessage.message,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }

    }
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(SubGreen.copy(alpha = 0.3f), RoundedCornerShape(200.dp)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_chatbot_profile),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}





