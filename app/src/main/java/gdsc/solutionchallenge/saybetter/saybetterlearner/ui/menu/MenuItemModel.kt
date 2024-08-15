package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu

data class MenuItemModel(
    val title: String,
    val iconRes: Int,
    val onClick: () -> Unit
)
