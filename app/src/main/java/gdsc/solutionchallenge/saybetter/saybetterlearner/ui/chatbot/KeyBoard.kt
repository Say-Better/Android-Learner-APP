package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.DarkGray
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray200
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White


@Composable
fun InputKeyboard(modifier: Modifier,
                  onCharacterClick: (Char) -> Unit,
                  onBackClick:() -> Unit,
                  onSpaceClick:() -> Unit
) {
    val num = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
    val list1 = listOf('ㅃ', 'ㅉ', 'ㄸ', 'ㄲ' ,'ㅆ', 'ㅒ', 'ㅖ', '~', '!', ' ')
    val list2 = listOf('ㅂ', 'ㅈ', 'ㄷ', 'ㄱ' ,'ㅅ', 'ㅛ', 'ㅕ', 'ㅑ', 'ㅐ', 'ㅔ')
    val list3 = listOf('ㅁ', 'ㄴ', 'ㅇ', 'ㄹ' ,'ㅎ', 'ㅗ', 'ㅓ', 'ㅏ', 'ㅣ', '?')
    val list4 = listOf(' ', 'ㅋ', 'ㅌ', 'ㅊ', 'ㅍ', 'ㅠ', 'ㅜ', 'ㅡ', '.')

    Column(modifier = modifier
        .fillMaxWidth(0.83f)
        .fillMaxHeight()
        .background(DarkGray, RoundedCornerShape(10.dp))
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        LazyRow() {
            itemsIndexed(num) { index, key ->
                Box(
                    modifier = modifier
                        .height(50.dp)
                        .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                        .background(Gray200, RoundedCornerShape(7.dp))
                        .clickable {
                            onCharacterClick(key)
                        }
                ) {
                    androidx.compose.material3.Text(
                        text = key.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W700,
                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
                if (index != num.lastIndex) {
                    Spacer(modifier = modifier.width(2.dp)) // 아이템 사이의 간격 조절
                }
            }
        }
        Spacer(modifier = modifier.height(2.dp))
        LazyRow() {
            itemsIndexed(list1) { index, latter ->
                if (index != num.lastIndex) {
                    Box(
                        modifier = modifier
                            .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .height(60.dp)
                            .background(White, RoundedCornerShape(7.dp))
                            .clickable {
                                onCharacterClick(latter)
                            },
                    ) {
                        androidx.compose.material3.Text(
                            text = latter.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W700,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = modifier.width(2.dp))
                }else {
                    Box(
                        modifier = modifier
                            .height(60.dp)
                            .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .background(MainGreen, RoundedCornerShape(7.dp))
                            .clickable {
                                onBackClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_keyboard_back),
                            contentDescription = null,
                            Modifier.size(35.dp))
                    }
                }
            }
        }
        Spacer(modifier = modifier.height(2.dp))

        LazyRow() {
            itemsIndexed(list2) { index, latter ->
                Box(
                    modifier = modifier
                        .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                        .height(60.dp)
                        .background(White, RoundedCornerShape(7.dp))
                        .clickable {
                            onCharacterClick(latter)
                        },
                ) {
                    androidx.compose.material3.Text(
                        text = latter.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W700,
                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = modifier.width(2.dp))
            }
        }

        Spacer(modifier = modifier.height(2.dp))

        LazyRow() {
            itemsIndexed(list3) { index, latter ->
                if (latter == ' ') {
                    Box(
                        modifier = modifier
                            .width(60.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .height(70.dp)
                            .background(Color.Transparent, RoundedCornerShape(7.dp))
                    ) {

                    }
                    Spacer(modifier = modifier.width(2.dp))
                }else {
                    Box(
                        modifier = modifier
                            .height(60.dp)
                            .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .background(White, RoundedCornerShape(7.dp))
                            .clickable {
                                onCharacterClick(latter)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Text(
                            text = latter.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W700,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = modifier.width(2.dp))
                }
            }
        }
        Spacer(modifier = modifier.height(2.dp))

        LazyRow() {
            itemsIndexed(list4) { index, latter ->
                if (latter == ' ') {
                    Box(
                        modifier = modifier
                            .width(140.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .height(60.dp)
                            .background(White, RoundedCornerShape(7.dp))
                            .clickable {
                                onSpaceClick()
                            },
                    ) {
                        androidx.compose.material3.Text(
                            text = "스페이스",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W700,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = modifier.width(2.dp))
                }else {
                    Box(
                        modifier = modifier
                            .height(60.dp)
                            .width(70.dp) // 아이템의 너비를 최소로 설정하여 필요한 공간만 차지하도록 합니다.
                            .background(White, RoundedCornerShape(7.dp))
                            .clickable {
                                onCharacterClick(latter)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Text(
                            text = latter.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W700,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = modifier.width(2.dp))
                }
            }
        }
    }
}