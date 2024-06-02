package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ComponentActivity.FeatureThatRequiresCameraPermission() {
    //카메라 권한 상태
    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    if(cameraPermissionState.status.isGranted) {
        Text(text = "카메라 권한이 부여됨")
        Log.d("permission", "camera permission granted")
    } else {
        Column {
            val textToShow = if(cameraPermissionState.status.shouldShowRationale) {
                // 사용자가 권한 요청을 거부했지만 근거를 제시할 수 있는 경우, 앱에 이 권한이 필요한 이유를 친절하게 설명합니다.
                "이 앱은 카메라가 중요합니다. 권한을 부여해주세요."
            } else {
                // 사용자가 이 기능을 처음 사용하거나, 사용자에게 이 권한을 다시 묻고 싶지 않은 경우 권한이 필요하다고 설명합니다.
                "이 기능을 사용하려면 카메라 권한이 필요합니다. " +
                        "권한을 부여해주세요."
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("권한 요청")
            }
        }
    }
}