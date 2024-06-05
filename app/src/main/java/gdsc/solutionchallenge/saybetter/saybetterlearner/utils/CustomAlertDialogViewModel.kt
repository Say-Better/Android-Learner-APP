package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class CustomAlertDialogViewModel : ViewModel() {
    val customAlertDialogState: MutableState<CustomAlertDialogState> = mutableStateOf<CustomAlertDialogState>(
        CustomAlertDialogState()
    )
    fun showCustomAlertDialog() {
        customAlertDialogState.value = CustomAlertDialogState(
            isVisible = true,
            title = "정말로 삭제하시겠습니깡?",
            description = "삭제하면 복구할 수 없습니당.",
            onClickConfirm = {
                resetDialogState()
            },
            onClickCancel = {
                resetDialogState()
            }
        )
    }
    // 다이얼로그 상태 초기화
    fun resetDialogState() {
        customAlertDialogState.value = CustomAlertDialogState()
    }
}