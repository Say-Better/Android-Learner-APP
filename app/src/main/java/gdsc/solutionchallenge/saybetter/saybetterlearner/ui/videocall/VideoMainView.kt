package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import androidx.camera.core.CameraSelector
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
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Transparent
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.tts.TTSManager

@Composable
fun ReadyMainView(
    isCameraOn : Boolean) {
    Box (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.85f),
        contentAlignment = Alignment.Center){
        Row (modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(Transparent, RoundedCornerShape(0.dp)),
            verticalAlignment = Alignment.CenterVertically){
            if (isCameraOn) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 622.dp, height = 370.dp)
                        .background(
                            color = DarkGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    LocalVideoRenderer(
                        modifier = Modifier
                            .size(width = 622.dp, height = 370.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }else {
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            if (isCameraOn) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(width = 622.dp, height = 370.dp)
                        .background(
                            color = DarkGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    RemoteVideoRenderer(
                        modifier = Modifier
                            .size(width = 622.dp, height = 370.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }else {
                Image(painter = painterResource(id = R.drawable.rectangle_1638),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun StartMainView(
    symbolSet : List<List<Symbol>>,
    ttsManager : TTSManager,
    commOptCnt: Int,
    ready : Boolean,
    selectedItemIndex: MutableState<Int?>,
    cnt :Int, ) {

    Row (modifier = Modifier
        .fillMaxHeight(0.82f)
        .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically){
        if (symbolSet[cnt - commOptCnt].size <=2) {
            symbolSet[cnt - commOptCnt].forEachIndexed{ index, item ->
                if(ready) {
                    Symbol(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        isSelected = index == selectedItemIndex.value,
                        symbol = item,
                        onSymbolClick = {
                            selectedItemIndex.value = index
                            ttsManager.speak(item.title)
                        },
                    )
                }else {
                    ReadySymbol(modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),)
                }
            }
        }else if (symbolSet[cnt - commOptCnt].size == 4) {
            symbolSet[cnt - commOptCnt].forEachIndexed{ index, item ->
                if(ready) {
                    Symbol(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp),
                        isSelected = index == selectedItemIndex.value,
                        symbol = item,
                        onSymbolClick = {
                            selectedItemIndex.value = index
                            ttsManager.speak(item.title)
                        },
                    )
                }else {
                    ReadySymbol(modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .height(250.dp),)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5), // 한 행에 4개의 아이템을 배치
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(symbolSet[cnt - commOptCnt]) { index, item ->
                    if(ready) {
                        Symbol(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .height(250.dp),
                            isSelected = index == selectedItemIndex.value,
                            symbol = item,
                            onSymbolClick = {
                                selectedItemIndex.value = index
                                ttsManager.speak(item.title)
                            },
                        )
                    }else {
                        ReadySymbol(modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .height(250.dp),)
                    }
                }
            }
        }
    }
}