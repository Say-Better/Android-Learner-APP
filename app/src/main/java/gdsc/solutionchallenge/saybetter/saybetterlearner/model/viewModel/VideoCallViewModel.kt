package gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.SymbolRecord
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType.*
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp

const val TEXT_SYMBOL = -1

class VideoCallViewModel:ViewModel(), MainService.InteractionListener {
    private var ttsManager: TTSManager? = null

    private val _symbolList = MutableStateFlow<List<Symbol>>(mutableListOf())
    val symbolList: StateFlow<List<Symbol>> = _symbolList

    private val _symbolRecord = MutableStateFlow<List<SymbolRecord>>(mutableListOf())
    val symbolRecord: StateFlow<List<SymbolRecord>> = _symbolRecord

    private val _selectedSymbolList = MutableStateFlow<List<Symbol>>(mutableListOf())
    val selectedSymbolList: StateFlow<List<Symbol>> = _selectedSymbolList

    private val _educationGoal = MutableStateFlow<String>("")
    val educationGoal: StateFlow<String> = _educationGoal

    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow<String>("")
    val description: StateFlow<String> = _description

    private val _commOptTimes = MutableStateFlow<Int>(0)
    val commOptTimes: StateFlow<Int> = _commOptTimes

    private val _commOptCnt = MutableStateFlow<Int>(0)
    val commOptCnt: StateFlow<Int> = _commOptCnt

    private val _iconState = MutableStateFlow(false)
    val iconState: StateFlow<Boolean> = _iconState

    private val _isStartLearning = MutableStateFlow<Boolean>(false)
    val isStartLearning: StateFlow<Boolean> = _isStartLearning

    private val _isEnding = MutableStateFlow<Boolean>(false)
    val isEnding: StateFlow<Boolean> = _isEnding

    private val _remoteGreetState = MutableStateFlow<Boolean>(false)
    val remoteGreetState: StateFlow<Boolean> = _remoteGreetState

    private val _localGreetState = MutableStateFlow<Boolean>(false)
    val localGreetState: StateFlow<Boolean> = _localGreetState

    private val _layoutState = MutableStateFlow<String>(SWITCH_TO_LAYOUT_1.name)
    val layoutState: StateFlow<String> = _layoutState

    private val _remoteSelectedSymbolId = MutableStateFlow<Int>(-1)
    val remoteSelectedSymbolId: StateFlow<Int> = _remoteSelectedSymbolId

    private val _isVideoOn = MutableStateFlow<Boolean>(false)
    val isVideoOn: StateFlow<Boolean> = _isVideoOn

    private val _isAudioOn = MutableStateFlow<Boolean>(false)
    val isAudioOn: StateFlow<Boolean> = _isAudioOn

    private val _chatState = MutableStateFlow<String>("")
    val chatState: StateFlow<String> = _chatState

    private val _longChatText = MutableStateFlow<String>("test")
    val longChatText: StateFlow<String> = _longChatText

    private val _isScreenSharing = MutableStateFlow<Boolean>(false)
    val isScreenSharing: StateFlow<Boolean> = _isScreenSharing

    private val _selectedItemIndex = MutableStateFlow<Int?>(null)
    val selectedItemIndex: StateFlow<Int?> = _selectedItemIndex

    fun setSelectedItemIndex(value: Int?) {
        _selectedItemIndex.value = value
    }

    fun setLongChatText(value: String) {
        _longChatText.value = getLongChatText() + "\n" + value
    }

    fun getLongChatText() : String {
        return _longChatText.value
    }

    fun setChatState(value: String) {
        _chatState.value = value
    }

    fun setAudioState(value: Boolean) {
        _isAudioOn.value = value
    }

    fun setVideoState(value: Boolean) {
        _isVideoOn.value = value
    }

    fun setTTSManager(value: TTSManager) {
        ttsManager = value
    }

    fun setIconState(value: Boolean) {
        _iconState.value = value
    }

    fun setIsStartLearning(value: Boolean) {
        _isStartLearning.value = value
    }

    fun setIsEnding(value: Boolean) {
        _isEnding.value = value
    }

    fun setRemoteGreetState(value: Boolean) {
        _remoteGreetState.value = value
    }

    fun setLocalGreetState(value: Boolean) {
        _localGreetState.value = value
    }

    fun setLayoutState(value: String) {
        _layoutState.value = value
    }

    fun addSelectedSymbolItem(symbol: Symbol) {
        Log.d("textSymbol", "[addSelectedSymbolItem] sym id: ${symbol.id}, sym text: ${symbol.title}")
        val newSelectedSymbols = _selectedSymbolList.value + symbol
        _selectedSymbolList.value = newSelectedSymbols
    }

    fun deleteSelectedSymbolItem(symbol: Symbol) {
        val newSelectedSymbols = _selectedSymbolList.value - symbol
        _selectedSymbolList.value = newSelectedSymbols
    }

    fun initVideoCall(title : String,
                      educationGoal:String,
                      description:String,
                      commOptTimes:Int,
                      commOptCnt:Int,
                      symbolList:List<Symbol>,
                      ttsManager: TTSManager) {
        _title.value = title
        _educationGoal.value = educationGoal
        _description.value = description
        _commOptTimes.value = commOptTimes
        _commOptCnt.value = commOptCnt
        _symbolList.value = symbolList

        MainService.interactionListener = this
        setTTSManager(ttsManager)
    }

    fun minusCnt() {
        _commOptCnt.value--
    }

    fun addRecord(symbol: Symbol?) {
        if(symbol != null) {
            _symbolRecord.value += SymbolRecord(
                symbol.id,
                _symbolRecord.value.size + 1L,
                getCurrentTimestamp()
            )
        }
    }

    fun getCurrentTimestamp(): Timestamp {
        return Timestamp(System.currentTimeMillis())
    }

    override fun onRemoteGreeting() {
        setRemoteGreetState(true)
        viewModelScope.launch {
            delay(1000) // 1초 동안 표시
            setRemoteGreetState(false)
        }
    }

    fun localGreeting() {
        setLocalGreetState(true)
        viewModelScope.launch {
            delay(1000)
            setLocalGreetState(false)
        }
    }

    override fun onSwitchToLearning() {
        setIsStartLearning(true)
    }

    override fun onSwitchToEnding() {
        setIsEnding(true)
    }

    override fun onSwitchToLayout1() {
        setLayoutState(SWITCH_TO_LAYOUT_1.name)
    }

    override fun onSwitchToLayout2() {
        setLayoutState(SWITCH_TO_LAYOUT_2.name)
    }

    override fun onSwitchToLayout4() {
        setLayoutState(SWITCH_TO_LAYOUT_4.name)
    }

    override fun onSwitchToLayoutAll() {
        setLayoutState(SWITCH_TO_LAYOUT_ALL.name)
    }

    override fun onSymbolSelect(symbolId: Int) {
        Log.d("textSymbol", "[onSymbolSelect] symboliId: $symbolId")
        val symbol: Symbol = _symbolList.value[symbolId] // 여기서 문제 발생. id가 한칸 밀려나면서 꼬인듯
        addSelectedSymbolItem(symbol)
    }

    override fun onSymbolDelete(symbolId: Int) {
        val symbol: Symbol = _symbolList.value[symbolId]
        deleteSelectedSymbolItem(symbol)
    }

    override fun onSymbolHighlight(symbolId: Int) {
        viewModelScope.launch {
            Log.d("textSymbol", "Highlight 시점 심볼id: $symbolId")
            val symbol: Symbol = _symbolList.value[symbolId]
            ttsManager?.speak(symbol.title)
            _remoteSelectedSymbolId.value = symbolId
            delay(2000)
            _remoteSelectedSymbolId.value = -1
        }
    }

    override fun onSetScreenSharing(isScreenSharing: Boolean) {
        _isScreenSharing.value = isScreenSharing
    }

    override fun onAddTextSymbol(symbolText: String) {
        val newSymbolId: Int = _symbolList.value.lastIndex + 1
        Log.d("textSymbol", "[onAddTextSymbol] new sym id: $newSymbolId, sym text: $symbolText")
        val newSymbol = Symbol(newSymbolId, symbolText, TEXT_SYMBOL)
        _symbolList.value += listOf(newSymbol)
//        for(item in _symbolList.value) {
//            Log.d("textSymbol", "[onAddTextSymbol-symList] id: ${item.id}, title: ${item.title} \n")
//        }
    }

    fun localSymbolHighlight(selectedItemIndex: Int, symbolTitle: String) {
        viewModelScope.launch {
            setSelectedItemIndex(selectedItemIndex)

            ttsManager?.speak(symbolTitle)
            delay(2000)
            setSelectedItemIndex(null)
        }
    }
}