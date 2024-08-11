package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot.chatbotinputlayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.Symbol
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun InputSymbol(modifier: Modifier, SymbolClick: (String) -> Unit) {

    val items = listOf(
        Symbol("1인분주세요", R.drawable.a1),
        Symbol("2인분주세요", R.drawable.a2),
        Symbol("2인분주세요", R.drawable.a3),
        Symbol("가르쳐주세요", R.drawable.a4),
        Symbol("간식", R.drawable.a5),
        Symbol("과자", R.drawable.a6),
        Symbol("과자주세요", R.drawable.a7),
        Symbol("그만먹을래요", R.drawable.a8),
        Symbol("배고파요", R.drawable.a9),
        Symbol("배달음식", R.drawable.a10),
        Symbol("배불러요", R.drawable.a11),
        Symbol("식혀주세요", R.drawable.a12),
        Symbol("양념치킨", R.drawable.a13),
        Symbol("양념해주세요", R.drawable.a14),
        Symbol("어떤 음식 좋아해요", R.drawable.a15),
        Symbol("오늘의 음식은 무엇인가요", R.drawable.a16),
        Symbol("음식", R.drawable.a17),
        Symbol("음식값이 싸요", R.drawable.a18),
        Symbol("이거 먹을래요", R.drawable.a19),
        Symbol("잘라주세요", R.drawable.a20),
        Symbol("정리해주세요", R.drawable.a21),
        Symbol("집에서 먹을래요", R.drawable.a22),
        Symbol("치킨", R.drawable.a23),
        Symbol("치킨 시켜주세요", R.drawable.a24),
        Symbol("피자", R.drawable.a25),
        Symbol("피자 시켜주세요", R.drawable.a26),
    )  // 임시로 10개의 아이템을 생성


    val itemsPerPage = 10
    val totalPages = (items.size + itemsPerPage - 1) / itemsPerPage

    var pagerState = rememberPagerState(
        pageCount = { totalPages },
        initialPage = 0
    )
    val scope = rememberCoroutineScope()


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
                ) {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
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
            HorizontalPager(
                state = pagerState,
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp)) {
                SymbolLayout(modifier, pagerState.currentPage, items, itemsPerPage, SymbolClick,)
            }
        }
        Box(
            modifier = modifier
                .background(SubGrey, RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                .fillMaxHeight()
                .clickable(
                ) {
                    scope.launch {
                        if (pagerState.currentPage < totalPages - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
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
fun SymbolLayout(modifier: Modifier, page: Int, items: List<Symbol>, itemsPerPage: Int, SymbolClick: (String) -> Unit) {

    val start = page* itemsPerPage
    val end = minOf(start + itemsPerPage, items.size)
    val pageItems = items.subList(start, end)

    LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement .spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(pageItems.size) { index ->
                Box (modifier = Modifier
                    .background(White, RoundedCornerShape(7.dp))
                    .padding(vertical = 10.dp)
                    .clickable {
                        SymbolClick(pageItems[index].title)
                    }){
                    Column (Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Image(
                            painter = painterResource(id = pageItems[index].img),
                            contentDescription = null,
                            Modifier
                                .size(100.dp)
                                .padding(end = 10.dp)
                        )
                        Text(text = pageItems[index].title,
                            color = Black)
                    }
                }
            }
        }


}