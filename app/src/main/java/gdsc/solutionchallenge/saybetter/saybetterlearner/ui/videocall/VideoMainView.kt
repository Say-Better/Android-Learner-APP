package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.imageView.localShakingImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.imageView.remoteShakingImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Transparent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.InstantInteractionType.*
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager

const val LOCAL = "local"
const val REMOTE = "remote"

@Composable
fun ReadyMainView(
    isCameraOn: Boolean,
    videoCallViewModel: VideoCallViewModel,
    isReadyView: Boolean,
    isScreenSharing: Boolean
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(
                // 준비 화면이거나 화면 공유중에는 큰 영역을 차지하도록 함
                if(isScreenSharing) 0.8f else if(isReadyView) 0.7f else 0.2f
            ),
        contentAlignment = Alignment.TopCenter
    ){
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(Transparent, RoundedCornerShape(0.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            // 화면공유 중이 아닐 때만 local 캠을 띄움
            if(!isScreenSharing) {
                if (isCameraOn) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(
                                width = if(isReadyView) 622.dp else 182.dp,
                                height = if(isReadyView) 370.dp else 108.dp
                            )
                            .background(
                                color = DarkGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        LocalVideoRenderer(
                            modifier = Modifier
                                .size(
                                    width = if(isReadyView) 622.dp else 182.dp,
                                    height = if(isReadyView) 370.dp else 108.dp
                                )
                                .clip(RoundedCornerShape(12.dp))
                        )

                        localShakingImage(videoCallViewModel)    //videocallviewmodel에 greetstate로 알수있음!
                    }
                }else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(
                                width = if(isReadyView) 622.dp else 182.dp,
                                height = if(isReadyView) 370.dp else 108.dp
                            )
                            .background(
                                color = DarkGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle_1638),
                            contentDescription = null,
                            modifier = Modifier
                                .size(
                                    width = if(isReadyView) 622.dp else 182.dp,
                                    height = if(isReadyView) 370.dp else 108.dp
                                )
                        )
                        localShakingImage(videoCallViewModel)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            if (isCameraOn) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(
                            width = if(isScreenSharing) 969.dp else if(isReadyView) 622.dp else 182.dp,
                            height = if(isScreenSharing) 575.dp else if(isReadyView) 370.dp else 108.dp
                        )
                        .background(
                            color = DarkGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    RemoteVideoRenderer(
                        modifier = Modifier
                            .size(
                                width = if(isScreenSharing) 969.dp else if(isReadyView) 622.dp else 182.dp,
                                height = if(isScreenSharing) 575.dp else if(isReadyView) 370.dp else 108.dp
                            )
                            .clip(RoundedCornerShape(12.dp))
                    )
                    remoteShakingImage(videoCallViewModel)
                }
            }else {
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            width = if(isScreenSharing) 969.dp else if(isReadyView) 622.dp else 182.dp,
                            height = if(isScreenSharing) 575.dp else if(isReadyView) 370.dp else 108.dp
                        )
                )
                remoteShakingImage(videoCallViewModel)
            }
        }
    }
}

@Composable
fun StartMainView(
    symbolSet: List<Symbol>,
    ttsManager: TTSManager,
    commOptCnt: Int,
    ready: Boolean,
    selectedItemIndex: Int?,
    cnt: Int,
    layoutState: String,
    selectedSymbolList: List<Symbol>,
    selectedSymbolIdState: Int,
    onSymbolClicked: (Int, Symbol) -> Unit
) {

    Row (modifier = Modifier
        .fillMaxHeight(0.82f)
        .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        when(layoutState) {
            SWITCH_TO_LAYOUT_1.name -> {
                val symbol = selectedSymbolList.getOrNull(0)
                if(symbol != null){
                    Symbol(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        // 로컬에서 선택되었거나, 원격으로 선택되었거나
                        isSelected = (0 == selectedItemIndex) || (symbol.id == selectedSymbolIdState),
                        symbol = symbol,
                        onSymbolClick = { onSymbolClicked(0, symbol) }
                    )
                } else {
                    ReadySymbol(modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),)
                }

            }
            SWITCH_TO_LAYOUT_2.name -> {
                for (i in 0 until 2) {
                    val symbol = selectedSymbolList.getOrNull(i)
                    if(symbol != null){
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            isSelected = (i == selectedItemIndex) || (symbol.id == selectedSymbolIdState),
                            symbol = symbol,
                            onSymbolClick = { onSymbolClicked(i, symbol) },
                        )
                    } else {
                        ReadySymbol(modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),)
                    }
                }
            }
            SWITCH_TO_LAYOUT_4.name -> {
                for (i in 0 until 4) {
                    val symbol = selectedSymbolList.getOrNull(i)
                    if(symbol != null){
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),
                            // 선택되는 기준: 로컬에서 선택하거나, 원격에서 선택하거나
                            isSelected = (i == selectedItemIndex) || (symbol.id == selectedSymbolIdState),
                            symbol = symbol,
                            onSymbolClick = { onSymbolClicked(i, symbol) },
                        )
                    } else {
                        ReadySymbol(modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp)
                        )
                    }
                }
            }
            SWITCH_TO_LAYOUT_ALL.name -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5), // 한 행에 5개의 아이템을 배치
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(symbolSet) { index, item ->
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),
                            isSelected = (index == selectedItemIndex) || (item.id == selectedSymbolIdState),
                            symbol = item,
                            onSymbolClick = { onSymbolClicked(index, item) },
                        )
                    }
                }
            }
            else -> {}
        }










//        if (symbolSet[cnt - commOptCnt].size <=2) { // 1, 2 view
//            symbolSet.forEachIndexed{ index, item ->
//                if(ready) {
//                    Symbol(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .weight(1f),
//                        isSelected = index == selectedItemIndex.value,
//                        symbol = item,
//                        onSymbolClick = {
//                            selectedItemIndex.value = index
//                            ttsManager.speak(item.title)
//                        },
//                    )
//                }else {
//                    ReadySymbol(modifier = Modifier
//                        .padding(8.dp)
//                        .weight(1f),)
//                }
//            }
//        }else if (symbolSet[cnt - commOptCnt].size == 4) { // 4 view
//            symbolSet.forEachIndexed{ index, item ->
//                if(ready) {
//                    Symbol(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .weight(1f)
//                            .height(250.dp),
//                        isSelected = index == selectedItemIndex.value,
//                        symbol = item,
//                        onSymbolClick = {
//                            selectedItemIndex.value = index
//                            ttsManager.speak(item.title)
//                        },
//                    )
//                }else {
//                    ReadySymbol(modifier = Modifier
//                        .padding(8.dp)
//                        .weight(1f)
//                        .height(250.dp),)
//                }
//            }
//        } else { // All View
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(5), // 한 행에 4개의 아이템을 배치
//                horizontalArrangement = Arrangement.Center,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                itemsIndexed(symbolSet[cnt - commOptCnt]) { index, item ->
//                    if(ready) {
//                        Symbol(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f)
//                                .height(250.dp),
//                            isSelected = index == selectedItemIndex.value,
//                            symbol = item,
//                            onSymbolClick = {
//                                selectedItemIndex.value = index
//                                ttsManager.speak(item.title)
//                            },
//                        )
//                    }else {
//                        ReadySymbol(modifier = Modifier
//                            .padding(8.dp)
//                            .weight(1f)
//                            .height(250.dp),)
//                    }
//                }
//            }
//        }




    }
}