package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

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
        Symbol(1,"가요", R.drawable.e1),
        Symbol(2,"감격하다", R.drawable.e2),
        Symbol(3,"놀라다", R.drawable.e3),
        Symbol(4,"따분하다", R.drawable.e4),
        Symbol(5,"떨리다", R.drawable.e5),
        Symbol(6,"미안하다", R.drawable.e6),
        Symbol(7,"민망하다", R.drawable.e7),
        Symbol(8,"반가워요", R.drawable.e8),
        Symbol(9,"밥", R.drawable.e9),
        Symbol(10,"배고파요", R.drawable.e10),
        Symbol(11,"부끄러워요", R.drawable.e11),
        Symbol(12,"부담스럽다", R.drawable.e12),
        Symbol(13,"부럽다", R.drawable.e13),
        Symbol(14,"뿌듯하다", R.drawable.e14),
        Symbol(15,"상처받다", R.drawable.e15),
        Symbol(16,"속상해요", R.drawable.e16),
        Symbol(17,"시작", R.drawable.e17),
        Symbol(18,"신나요", R.drawable.e18),
        Symbol(19,"어리둥절하다", R.drawable.e19),
        Symbol(20,"우울해요", R.drawable.e20),
        Symbol(21,"자랑스럽다", R.drawable.e21),
        Symbol(22,"짜증나다", R.drawable.e22),
        Symbol(23,"화나요", R.drawable.e23),
        Symbol(24,"흥미롭다", R.drawable.e24),
        Symbol(25,"1인분주세요", R.drawable.a1),
        Symbol(26,"2인분주세요", R.drawable.a2),
        Symbol(27,"2인분주세요", R.drawable.a3),
        Symbol(28,"가르쳐주세요", R.drawable.a4),
        Symbol(29,"간식", R.drawable.a5),
        Symbol(30,"과자", R.drawable.a6),
        Symbol(31,"과자주세요", R.drawable.a7),
        Symbol(32,"그만먹을래요", R.drawable.a8),
        Symbol(33,"배고파요", R.drawable.a9),
        Symbol(34,"배달음식", R.drawable.a10),
        Symbol(35,"배불러요", R.drawable.a11),
        Symbol(36,"식혀주세요", R.drawable.a12),
        Symbol(37,"양념치킨", R.drawable.a13),
        Symbol(38,"양념해주세요", R.drawable.a14),
        Symbol(39,"어떤 음식 좋아해요", R.drawable.a15),
        Symbol(40,"오늘의 음식은 무엇인가요", R.drawable.a16),
        Symbol(41,"음식", R.drawable.a17),
        Symbol(42,"음식값이 싸요", R.drawable.a18),
        Symbol(43,"이거 먹을래요", R.drawable.a19),
        Symbol(44,"잘라주세요", R.drawable.a20),
        Symbol(45,"정리해주세요", R.drawable.a21),
        Symbol(46,"집에서 먹을래요", R.drawable.a22),
        Symbol(47,"치킨", R.drawable.a23),
        Symbol(48,"치킨 시켜주세요", R.drawable.a24),
        Symbol(49,"피자", R.drawable.a25)
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