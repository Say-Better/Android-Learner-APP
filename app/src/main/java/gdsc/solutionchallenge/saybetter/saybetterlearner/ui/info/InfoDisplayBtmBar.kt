package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import androidx.compose.runtime.Composable
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel

@Composable
fun DisplayBottomBar(
    mode: Int,
    onClickComplete:()->Unit,
    onClickFinish:()->Unit) {
    if (mode == 0) {
        FinishBtmBar {
            onClickComplete()
        }
    } else {
        LoginFinishBtmBar {
            onClickFinish()
        }
    }
}