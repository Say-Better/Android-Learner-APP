package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import androidx.compose.runtime.MutableState
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting.SettingActivity

data class CustomAlertDialogState(
    val code: String = "",
    val onClickCancel: () -> Unit = {},
)

fun resetDialogState(state: MutableState<CustomAlertDialogState>) {
    state.value = CustomAlertDialogState()
}

fun customAlertDialog(customAlertDialogState: MutableState<SettingActivity.CustomAlertDialogState>) {
}