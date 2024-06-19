@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

const val TAG = "PermissionCheck"

@Composable
fun PermissionCheckDialog() {

    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )
    var dialogVisible by remember { mutableStateOf(true) }

    // 카메라 권한이 없을때만 표시
    if(!cameraPermissionState.status.isGranted and dialogVisible) {
        AlertDialog(
            onDismissRequest = { dialogVisible = false },
            content = {
                CardExample(
                    onDismissRequest = {},
                    onConfirmClick = {}
                )
            }
        )
    } else {
        Log.d(TAG, "Permission Already Granted.")
    }

}

@Composable
fun CardExample(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Card example",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "This is an example of a card with buttons.",
                style = MaterialTheme.typography.body1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text("Dismiss")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = onConfirmClick
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Preview
@Composable
fun PermissionCheckDialogPreview() {
    PermissionCheckDialog()
}