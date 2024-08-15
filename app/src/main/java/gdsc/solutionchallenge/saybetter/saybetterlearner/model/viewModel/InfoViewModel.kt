package gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InfoViewModel:ViewModel() {
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _birthDay = MutableStateFlow("")
    val birthDay :StateFlow<String> = _birthDay

    private val _name = MutableStateFlow("")
    val name :StateFlow<String> = _name

    private val _gender = MutableStateFlow(false)
    val gender :StateFlow<Boolean> = _gender

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet

    private val _customAlertDialogState = MutableStateFlow(CustomAlertDialogState())
    val customAlertDialogState: StateFlow<CustomAlertDialogState> = _customAlertDialogState


    private val _mode = MutableStateFlow(0)
    val mode: StateFlow<Int> = _mode

    fun setMode(mode: Int) {
        _mode.value = mode
    }

    fun setCustomAlertDialogState(state: CustomAlertDialogState) {
        _customAlertDialogState.value = state
    }

    fun resetCustomAlertDialogState() {
        _customAlertDialogState.value = CustomAlertDialogState()
    }

    fun setImageUri(uri : Uri) {
        _imageUri.value = uri
    }

    fun setBirthDay(birthDay : String) {
        _birthDay.value = birthDay
    }

    fun setName(name : String) {
        _name.value = name
    }

    fun setGender(gender : Boolean) {
        _gender.value = gender
    }

    fun setShowBottomSheet(show: Boolean) {
        _showBottomSheet.value = show
    }
}