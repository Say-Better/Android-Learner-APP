package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

@Composable
fun Modifier.clickWithScaleAnimation(
    onClick: () -> Unit,
    scaleDown: Float = 0.9f, // 작아질 크기 비율
    animationDuration: Int = 100 // 애니메이션 지속 시간
): Modifier {
    // 클릭 상태를 기억하는 변수
    var isClicked by remember { mutableStateOf(false) }

    // 클릭 시 크기 변화를 위한 애니메이션 값
    val scale by animateFloatAsState(
        targetValue = if (isClicked) scaleDown else 1f,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val clickableModifier = Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null // CustomClickEvent를 사용하지 않으므로 null
    ) {
        isClicked = true // 클릭 시 애니메이션 시작
        onClick() // 클릭 이벤트 실행
    }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(animationDuration.toLong())
            isClicked = false // 애니메이션 후 원래 크기로 복구
        }
    }

    return this.then(
        clickableModifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
    )
}