package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.webrtc.service.MainService

@Composable
fun LocalVideoRenderer(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            MainService.localSurfaceView!!
        },
        update = { surfaceViewRenderer ->
            // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
        }
    )
}

@Composable
fun RemoteVideoRenderer(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            MainService.remoteSurfaceView!!
        },
        update = { surfaceViewRenderer ->
            // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
        }
    )
}