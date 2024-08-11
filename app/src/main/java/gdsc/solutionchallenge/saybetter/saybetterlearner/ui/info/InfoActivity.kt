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

    private val titleList = listOf("로그인에 성공했어요! 시작하기 전 기본 설정이 필요해요.",
        "모든 준비가 완료되었어요! 이제 시작해볼까요?")

    private val descriptionList = listOf("프로필 사진은 교육자에게 노출되어 학습자를 구별하는 것에 사용돼요.",
        "마지막으로 원활한 사용을 위해 앱 내 권한을 허용해주세요.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraImage = CameraImage(this)

        memberService = MemberService()
        memberService.setMemberInfoView(this)

        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        viewModel.setImageUri(GetImageUri().getDrawableUri(this@InfoActivity, R.drawable.info_img)!!)  //기본 이미지

        setContent {
            InfoScreen()
        }

    }

    @Composable
    fun InfoScreen() {
        val showBottomSheet by viewModel.showBottomSheet.collectAsState()
        val customAlertDialogState by viewModel.customAlertDialogState.collectAsState()
        val mode by viewModel.mode.collectAsState()

        val context = LocalContext.current
        /** 요청할 권한 **/
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )

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

        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // 사진 선택 이후 돌아왔을 때 콜백
            if (uri != null) {
                viewModel.setImageUri(uri)
            } else {
                // 선택된 사진이 없을 경우
            }
        }

        if (customAlertDialogState.code.isNotEmpty()) {
            CalendarDialog().CalendarDialogScreen(
                onClickCancel = {
                    viewModel.resetCustomAlertDialogState()
                },
                this@InfoActivity
            )
        }

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
            ) {
                if (showBottomSheet) {
                    InfoProfileDialog().BottomSheet(onClickDismiss = {
                        viewModel.setShowBottomSheet(false)
                    }, onClickCamera = {
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
                Text(
                    text = titleList[mode],
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W600
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = descriptionList[mode],
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Gray500
                )

                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    if (mode==0) {
                        ImageScreen(viewModel, this@InfoActivity)
                        Spacer(modifier = Modifier.width(40.dp))
                        Column {
                            InfoNameTextField(viewModel)
                            Spacer(modifier = Modifier.height(20.dp))
                            InfoBirthTextField(viewModel)
                            Spacer(modifier = Modifier.height(20.dp))
                            InfoGender(
                                viewModel,
                                clickMale = { viewModel.setGender(false) },
                                clickFemale = { viewModel.setGender(true) })
                        }
                    }else {
                        Image(painter = painterResource(id = R.drawable.img_login_finish),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                                .height(430.dp),)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                if(mode ==0) {
                    FinishBtmBar(onClickFinish = {
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
                    })
                }else {
                    LoginFinishBtmBar(onClickFinish = {
                        startActivity(Intent(this@InfoActivity, MenuActivity::class.java).apply {
                            putExtra("userid", intent.getStringExtra("userid"))
                            putExtra("googleToken", intent.getStringExtra("googleToken"))
                        })
                    })
                }
            }
        }
    }
    override fun onImageCaptured(uri: Uri?) {
        uri?.let {
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

@Composable
fun ImageScreen(viewModel: InfoViewModel, context: Context) {
    val profileImageUri by viewModel.imageUri.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .size(320.dp)
            .clip(RoundedCornerShape(16.dp))) {
            AsyncImage(
                model = profileImageUri,
                contentDescription = null,
                modifier = Modifier.size(320.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(BoxBackground, RoundedCornerShape(50.dp))
                .width(320.dp)
                .height(60.dp)
                .clickable {
                    viewModel.setShowBottomSheet(true)
                },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera_info),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "프로필 설정",
                    fontSize = 20.sp
                )
            }
        }
        Text(text = "기본 이미지 사용",
            modifier = Modifier
                .clickable { viewModel.setImageUri(GetImageUri().getDrawableUri(context, R.drawable.info_img)!! )})
    }
}
@Composable
fun InfoGender(
    viewModel: InfoViewModel,
    clickMale:()->Unit,
    clickFemale:() ->Unit) {

    val gender by viewModel.gender.collectAsState()

    Column {
        Text(text = "성별",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.width(580.dp)){
            Box (modifier = Modifier
                .background(
                    if (!gender) MainGreen
                    else BoxBackground,
                    RoundedCornerShape(20.dp)
                )
                .weight(1f)
                .height(70.dp)
                .clickable {
                    clickMale()
                },
                contentAlignment = Alignment.Center

            ){
                Text(text = "남성",
                    fontSize = 20.sp,
                    color = if(!gender) White
                    else SubGrey,
                    fontWeight = FontWeight.W600)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box (modifier = Modifier
                .background(
                    if (gender) MainGreen
                    else BoxBackground,
                    RoundedCornerShape(20.dp)
                )
                .weight(1f)
                .height(70.dp)
                .clickable {
                    clickFemale()
                },
                contentAlignment = Alignment.Center

            ){
                Text(text = "여성",
                    fontSize = 20.sp,
                    color = if(gender) White
                    else SubGrey,
                    fontWeight = FontWeight.W600)
            }
        }
    }
}
@Composable
fun InfoNameTextField(viewModel: InfoViewModel) {

    val name by viewModel.name.collectAsState()

    Column {
        Text(text = "이름",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = name,
            onValueChange = { viewModel.setName(it)},
            trailingIcon = {
                if (name.isNotEmpty()) {
                    IconButton(onClick = {  }) {
                        Image(painter = painterResource(id = R.drawable.ic_textfield_delete),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp))
                    }
                }
            },
            modifier = Modifier
                .border(1.dp, Gray500, RoundedCornerShape(20.dp))
                .width(580.dp)
                .height(70.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White, // 배경색 설정
                focusedIndicatorColor = Color.Transparent, // 포커스된 상태의 밑줄 제거
                unfocusedIndicatorColor = Color.Transparent, // 비포커스 상태의 밑줄 제거
                disabledIndicatorColor = Color.Transparent // 비활성화 상태의 밑줄 제거
            ),
            textStyle = TextStyle(
                fontSize = 20.sp, // 텍스트 크기 설정
                lineHeight = 30.sp,
            ),
            singleLine = true, // 단일 줄로 설정
            maxLines = 1, // 최대 줄 수 설정
        )
    }
}

@Composable
fun InfoBirthTextField(viewModel: InfoViewModel) {
    val birthday by viewModel.birthDay.collectAsState()

    Column {
        Text(text = "생년월일",
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .border(1.dp, Gray500, RoundedCornerShape(20.dp))
                .width(580.dp)
                .height(70.dp)
                .clickable {
                    viewModel.setCustomAlertDialogState(CustomAlertDialogState(code = "open"))
                },
            contentAlignment = Alignment.CenterStart
        ){
            Text(text = birthday,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 15.dp))
        }
    }
}

@Composable
fun FinishBtmBar(onClickFinish:() ->Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()){
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
            .background(White)
            .weight(1f)
            .clickable {
                onClickFinish()
            },
            contentAlignment = Alignment.Center){
            Text(text = "다음",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600)
        }
    }
}

@Composable
fun LoginFinishBtmBar(onClickFinish:() ->Unit) {
    Row (modifier = Modifier
        .fillMaxWidth()){
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MainGreen, RoundedCornerShape(100.dp))
            .weight(1f)
            .clickable {
                onClickFinish()
            },
            contentAlignment = Alignment.Center){
            Text(text = "시작하기",
                fontSize = 25.sp,
                fontWeight = FontWeight.W600,
                color = White)
        }
    }
}



