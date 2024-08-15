package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.info

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.member.MemberInfoRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service.MemberService
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberInfoView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.viewModel.InfoViewModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.CalendarCallback
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.CalendarDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.InfoProfileDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Gray500
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.CustomAlertDialogState
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.CameraImage
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.CameraImageCallback
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.GetImageUri
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.permission.checkAndRequestPermissions


class InfoActivity : ComponentActivity(), CameraImageCallback, CalendarCallback, MemberInfoView{

    private lateinit var cameraImage: CameraImage
    private lateinit var memberService: MemberService

    private lateinit var viewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraImage = CameraImage(this)

        memberService = MemberService()
        memberService.setMemberInfoView(this)

        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        viewModel.setImageUri(GetImageUri().getDrawableUri(this@InfoActivity, R.drawable.info_img)!!)  //기본 이미지

        setContent {
            val customAlertDialogState by viewModel.customAlertDialogState.collectAsState()
            if (customAlertDialogState.code.isNotEmpty()) {
                CalendarDialog().CalendarDialogScreen(
                    onClickCancel = {
                        viewModel.resetCustomAlertDialogState()
                    },
                    this@InfoActivity
                )
            }
            val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // 사진 선택 이후 돌아왔을 때 콜백
                if (uri != null) {
                    viewModel.setImageUri(uri)
                } else {
                    // 선택된 사진이 없을 경우
                }
            }
            InfoView(
                viewModel = viewModel,
                onClickFinish = {
                    startActivity(Intent(this@InfoActivity, MenuActivity::class.java).apply {
                        putExtra("userid", intent.getStringExtra("userid"))
                        putExtra("googleToken", intent.getStringExtra("googleToken"))
                    })
                },
                onClickComplete = {
                    if (viewModel.name.value != "" && viewModel.birthDay.value != "") {
                        Log.d(
                            "request",
                            viewModel.name.value + " " + viewModel.birthDay.value + " " + viewModel.imageUri.value + " " + viewModel.gender.value
                        )
                        memberService.postMemberInfo(
                            getSharedPreferences("Member", Context.MODE_PRIVATE).getString(
                                "Jwt",
                                ""
                            )!!,
                            this@InfoActivity,
                            viewModel.imageUri.value,
                            MemberInfoRequest(
                                viewModel.name.value,
                                viewModel.name.value.replace("-", "."),
                                if (viewModel.gender.value) "F" else "M"
                            )
                        )
                    }
                },
                cameraImage = cameraImage,
                pickMedia
            )
        }

    }

    override fun onImageCaptured(uri: Uri?) {
        uri?.let {
            Log.d("uri", "변경")
            viewModel.setImageUri(it)
        }
    }

    override fun onImageCaptureFailed() {
    }

    override fun onDateClickEvent(date: String) {
        viewModel.setBirthDay(date)
    }

    override fun onPostMemberInfoSuccess(response: GeneralResponse<String>) {
        val spf = getSharedPreferences("Member", Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.apply()
        viewModel.setMode(1)
    }

    override fun onPostMemberInfoFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }
}



