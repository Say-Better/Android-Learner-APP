package gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.SymbolRecord
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.sql.Timestamp

class VideoCallViewModel:ViewModel() {
    private val _symbolList = MutableStateFlow<List<List<Symbol>>>(mutableListOf())
    val symbolList: StateFlow<List<List<Symbol>>> = _symbolList

    private val _symbolRecord = MutableStateFlow<List<SymbolRecord>>(mutableListOf())
    val symbolRecord: StateFlow<List<SymbolRecord>> = _symbolRecord

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

    private val _greetState = MutableStateFlow(false)
    val greetState : StateFlow<Boolean> = _greetState

    fun setGreetState(value: Boolean) {
        _greetState.value = value
    }

    fun setIconState(value: Boolean) {
        _iconState.value = value
    }

    fun initVideoCall(title : String,
                      educationGoal:String,
                      description:String,
                      commOptTimes:Int,
                      commOptCnt:Int,
                      symbolList:List<List<Symbol>>) {
        _title.value = title
        _educationGoal.value = educationGoal
        _description.value = description
        _commOptTimes.value = commOptTimes
        _commOptCnt.value = commOptCnt
        _symbolList.value = symbolList
    }

    fun minusCnt() {
        _commOptCnt.value--
    }

    fun addRecord(symbol: Symbol?) {
        if(symbol != null) {
            _symbolRecord.value = _symbolRecord.value + SymbolRecord(
                symbol.id,
                _symbolRecord.value.size + 1L,
                getCurrentTimestamp()
            )
        }
    }

    fun getCurrentTimestamp(): Timestamp {
        return Timestamp(System.currentTimeMillis())
    }
}