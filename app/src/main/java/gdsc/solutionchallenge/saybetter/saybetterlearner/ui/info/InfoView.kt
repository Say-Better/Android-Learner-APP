package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.CalendarDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.InfoProfileDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.CameraImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission.checkAndRequestPermissions
import java.net.URI

@Composable
fun InfoView(
    viewModel : InfoViewModel,
    onClickFinish:() ->Unit,
    onClickComplete:() ->Unit,
    cameraImage: CameraImage,
    pickMedia : ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val showBottomSheet by viewModel.showBottomSheet.collectAsState()
    val mode by viewModel.mode.collectAsState()

    val context = LocalContext.current


    Surface {
        Column(modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)) {
            showBottomSheet.let { if (it) ShowInfoProfileDialog(viewModel, cameraImage, pickMedia) }
            DisplayTexts(mode)
            Spacer(modifier = Modifier.height(30.dp))
            DisplayContent(viewModel,mode,context)
            Spacer(modifier = Modifier.height(20.dp))
            DisplayBottomBar(mode,
               onClickFinish = {onClickFinish()},
                onClickComplete = {onClickComplete()})
        }
    }
}

@Composable
fun ShowInfoProfileDialog(
    viewModel: InfoViewModel,
    cameraImage:CameraImage,
    pickMedia : ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val context = LocalContext.current
    val permissions = arrayOf(Manifest.permission.CAMERA)
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        /** 권한 요청시 동의 했을 경우 **/
        if (areGranted) {
            Log.d("test5", "권한이 동의되었습니다.")
            cameraImage.dispatchTakePictureIntent()
        }
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("test5", "권한이 거부되었습니다.")
        }
    }

    InfoProfileDialog().BottomSheet(
        onClickDismiss = {
            viewModel.setShowBottomSheet(false)
        },
        onClickCamera = {
            checkAndRequestPermissions(
                context,
                permissions,
                launcherMultiplePermissions,
                onPermissionsGranted = {    //권한이 이미 다 있을 때
                    cameraImage.dispatchTakePictureIntent()
                }
            )
        viewModel.setShowBottomSheet(false)
    }, onClickGallary = {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        viewModel.setShowBottomSheet(false)
    })
}

