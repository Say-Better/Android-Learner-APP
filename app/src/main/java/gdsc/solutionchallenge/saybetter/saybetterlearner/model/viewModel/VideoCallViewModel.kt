package gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.SymbolRecord
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType.*
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp

class VideoCallViewModel:ViewModel(), MainService.InteractionListener {
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

    private val _greetState = MutableStateFlow<Boolean>(false)
    val greetState: StateFlow<Boolean> = _greetState

    private val _localGreetState = MutableStateFlow<Boolean>(false)
    val localGreetState: StateFlow<Boolean> = _localGreetState

    private val _layoutState = MutableStateFlow<String>(SWITCH_TO_LAYOUT_1.name)
    val layoutState: StateFlow<String> = _layoutState

    fun setIconState(value: Boolean) {
        _iconState.value = value
    }

    fun setIsStartLearning(value: Boolean) {
        _isStartLearning.value = value
    }

    fun setGreetState(value: Boolean) {
        _greetState.value = value
    }

    fun setLocalGreetState(value: Boolean) {
        _localGreetState.value = value
    }

    fun setLayoutState(value: String) {
        _layoutState.value = value
    }

    fun addSelectedSymbolItem(symbol: Symbol) {
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
                      symbolList:List<Symbol>) {
        _title.value = title
        _educationGoal.value = educationGoal
        _description.value = description
        _commOptTimes.value = commOptTimes
        _commOptCnt.value = commOptCnt
        _symbolList.value = symbolList

        MainService.interactionListener = this
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

    override fun onGreeting() {
        setGreetState(true)
        viewModelScope.launch {
            delay(1000) // 1초 동안 표시
            setGreetState(false)
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

    override fun onSymbolHighlight() {
    }
}