package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.CamCoder

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope

@Composable
fun CameraComponet(
    modifier: Modifier,
    context : Context,
    cameraSelectorState: CameraSelector
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val imageCapture = remember {ImageCapture.Builder().build()}
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = cameraProviderFuture.get()
    val preview = Preview.Builder().build()

    LaunchedEffect (cameraProviderFuture, cameraSelectorState) {
        cameraProviderFuture.addListener({
            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelectorState,
                    preview,
                    imageCapture
                )
            }catch (exc : Exception) {
                Log.d("camera", exc.message.toString())
            }
        }, ContextCompat.getMainExecutor(context))
    }

    AndroidView(modifier = modifier
        .clip(RoundedCornerShape(10.dp)),
        factory = {ctx->
            PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { previewView ->
            preview.setSurfaceProvider(previewView.surfaceProvider)
        })
}