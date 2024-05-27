package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.inputLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.CustomClickEvent

@Composable
fun InputSymbol(modifier: Modifier) {
    var currentPage by remember { mutableStateOf(0) }
    val items = List(10) { it } // 임시로 10개의 아이템을 생성
    val itemsPerPage = 8
    val totalPages = (items.size + itemsPerPage - 1) / itemsPerPage

    Row(
        modifier = modifier
            .fillMaxWidth(0.83f)
            .fillMaxHeight()
    ) {
        Box(
            modifier = modifier
                .background(SubGrey, RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    if (currentPage > 0) currentPage--
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                modifier = modifier.size(28.dp)
            )
        }
        Box(
            modifier = modifier
                .background(DarkGray)
                .fillMaxHeight()
                .weight(1f)
        ) {
            SymbolLayout(modifier, currentPage, items, itemsPerPage)
        }
        Box(
            modifier = modifier
                .background(SubGrey, RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    if (currentPage < totalPages - 1) currentPage++
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_forward_arrow),
                contentDescription = null,
                modifier = modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun SymbolLayout(modifier: Modifier, currentPage: Int, items: List<Int>, itemsPerPage: Int) {
    val start = currentPage * itemsPerPage
    val end = minOf(start + itemsPerPage, items.size)
    val pageItems = items.subList(start, end)

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(pageItems.size) { index ->
            //clickable 지정해서 input text에 넣어주기
            Image(
                painter = painterResource(id = R.drawable.symbol_big),
                contentDescription = null,
                Modifier.size(160.dp)
                    .padding(end = 10.dp)
            )
        }
    }
}
