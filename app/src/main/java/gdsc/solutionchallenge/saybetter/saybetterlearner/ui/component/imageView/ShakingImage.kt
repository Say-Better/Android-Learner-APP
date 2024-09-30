package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.imageView

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.VideoCallViewModel
import kotlinx.coroutines.delay

@Composable
fun localShakingImage(
    videoCallViewModel: VideoCallViewModel
) {
    val localGreetState = videoCallViewModel.localGreetState.collectAsState()
    // Control the shaking animation
    var rotationDegree by remember { mutableStateOf(0f) }

    val rotationAnimation by animateFloatAsState(
        targetValue = rotationDegree,
        animationSpec = tween(durationMillis = 200, delayMillis = 0)
    )

    // Start shaking animation when greetState.value is true
    LaunchedEffect(localGreetState.value) {
        if (localGreetState.value) {
            repeat(5) { // Number of shake cycles
                rotationDegree = 5f // Rotate right
                delay(200) // Duration of rotation
                rotationDegree = -5f // Rotate left
                delay(200) // Duration of rotation
            }
            rotationDegree = 0f // Reset to original position
            videoCallViewModel.setLocalGreetState(false)
        }
    }

    if (localGreetState.value) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer(
                    rotationZ = rotationAnimation // Apply rotation animation
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_greeting),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun remoteShakingImage(
    videoCallViewModel: VideoCallViewModel
) {
    val remoteGreetState = videoCallViewModel.remoteGreetState.collectAsState()
    // Control the shaking animation
    var rotationDegree by remember { mutableStateOf(0f) }

    val rotationAnimation by animateFloatAsState(
        targetValue = rotationDegree,
        animationSpec = tween(durationMillis = 200, delayMillis = 0)
    )

    // Start shaking animation when greetState.value is true
    LaunchedEffect(remoteGreetState.value) {
        if (remoteGreetState.value) {
            repeat(5) { // Number of shake cycles
                rotationDegree = 5f // Rotate right
                delay(200) // Duration of rotation
                rotationDegree = -5f // Rotate left
                delay(200) // Duration of rotation
            }
            rotationDegree = 0f // Reset to original position
            videoCallViewModel.setRemoteGreetState(false)
        }
    }

    if (remoteGreetState.value) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer(
                    rotationZ = rotationAnimation // Apply rotation animation
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_greeting),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
