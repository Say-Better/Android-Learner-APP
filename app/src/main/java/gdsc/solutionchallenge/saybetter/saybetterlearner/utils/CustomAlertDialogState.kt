package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

data class CustomAlertDialogState(
    val isVisible: Boolean = false,
    val title: String = "",
    val description: String = "",
    val onClickConfirm: () -> Unit = {},
    val onClickCancel: () -> Unit = {},
)