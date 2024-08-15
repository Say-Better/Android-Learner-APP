package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import kotlinx.coroutines.launch

class ChatBotActivity: ComponentActivity(), ChatBotView {

    private lateinit var chatBotViewModel: ChatBotViewModel
    private lateinit var chatService: ChatService

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatBotViewModel = ChatBotViewModel(context = this)

        chatService = ChatService()
        chatService.setChatBotView(this@ChatBotActivity)

        setContent {
            ChatBatPreview(
                this,
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





