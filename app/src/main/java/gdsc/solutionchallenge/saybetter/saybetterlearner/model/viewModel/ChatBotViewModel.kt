package gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.sqlite.ChatDatabaseHelper
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSListener
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatBotViewModel(private val context: Context) : ViewModel(), TTSListener {
    private val _chatMessageList = MutableStateFlow(listOf<ChatMessage>())
    val chatMessageList: StateFlow<List<ChatMessage>> = _chatMessageList

    private val _selectedMessageIndex = MutableStateFlow(0)
    val selectedMessageIndex: StateFlow<Int> = _selectedMessageIndex

    private val _highlightedRange = MutableStateFlow(Pair(-1, -1))
    val highlightedRange: StateFlow<Pair<Int, Int>> = _highlightedRange

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking :StateFlow<Boolean> = _isSpeaking


    private val ttsManager: TTSManager = TTSManager(context).apply {
        setTTSListener(this@ChatBotViewModel)
    }

    private val chatDB : ChatDatabaseHelper = ChatDatabaseHelper(context)

    init {
        _chatMessageList.value = chatDB.getAllChats()
        if (_chatMessageList.value.isNullOrEmpty()) addMessage(ChatMessage(false, "우리 한 번 대화를 시작해볼까? 만나서 반가워~", 0))
    }

    fun addMessage(message : ChatMessage) {
        _chatMessageList.value = _chatMessageList.value + ChatMessage(
            isUser = message.isUser,
            message = message.message,
            symbol = message.symbol
        )
        chatDB.addChat(message)
        selectMessage(_chatMessageList.value.size -1)
        speak(message.message)
    }

    fun selectMessage(index : Int) {
        _selectedMessageIndex.value = index
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    override fun onTTSStarted() {
        _isSpeaking.value = true
    }

    override fun onTTSStopped() {
        updateIndex(-1,-1)
        _isSpeaking.value = false
    }

    override fun updateIndex(start : Int, end:Int) {
        Log.d("Highlighting", "Updating index: start=$start, end=$end")
        _highlightedRange.value = Pair(start, end)
    }

}