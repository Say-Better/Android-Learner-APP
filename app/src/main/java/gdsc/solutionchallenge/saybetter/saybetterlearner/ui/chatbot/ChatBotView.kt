package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatRoom
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service.ChatService
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.ChatBotViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.navibar.NaviMenu
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ChatBatPreview(
    context: Context,
    chatBotViewModel : ChatBotViewModel,
    chatService: ChatService,
    accessToken:String) {

    val chatMessageList = chatBotViewModel.chatMessageList.collectAsState(initial = emptyList())
    val selectedMessageIndex = chatBotViewModel.selectedMessageIndex.collectAsState()
    val highlightingRange = chatBotViewModel.highlightedRange.collectAsState()
    val isSpeaking = chatBotViewModel.isSpeaking.collectAsState()


    val chatRoomList = listOf(
        ChatRoom(
            "승아의 채팅방",
            "감정표현 상황",
            R.drawable.symbol
        )
    )
    Surface {
        Row(modifier = Modifier.fillMaxSize()) {
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            NaviMenu(2)

            ChatList(chatRoomList)

            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
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
                ChatInput(context,onClickTransmit = { inputText ->
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