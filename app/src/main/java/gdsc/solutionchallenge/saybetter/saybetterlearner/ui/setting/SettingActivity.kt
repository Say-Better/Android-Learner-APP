package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.setting

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGetResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service.MemberService
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberCodeView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberGetView
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog.LearnerCodeDialog
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.navibar.NaviMenu
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.BoxBackground
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.SubGrey
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White

class SettingActivity: ComponentActivity(), MemberCodeView, MemberGetView {

    private lateinit var memberService: MemberService
    private lateinit var customAlertDialogState: MutableState<CustomAlertDialogState>
    private lateinit var memberInfo : MutableState<MemberGetResponse>
    data class CustomAlertDialogState(
        val code: String = "",
        val onClickCancel: () -> Unit = {},
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        memberService = MemberService()
        memberService.setMemberCodeView(this)
        memberService.setMemberGetView(this)

        memberService.getMemberInfo(getSharedPreferences("Member", Context.MODE_PRIVATE).getString("Jwt", "")!!)

        memberInfo = mutableStateOf(MemberGetResponse("", 0, "", ""))
        customAlertDialogState = mutableStateOf(CustomAlertDialogState())

        setContent {
            Surface {
                Row(Modifier.fillMaxSize()) {
                    NaviMenu(mode = 3)
                    SettingView(
                        memberInfo = memberInfo,
                        onClickGenCode = {
                            memberService.getMemberCode(getSharedPreferences("Member", Context.MODE_PRIVATE).getString("Jwt", "")!!)
                        })
                }
                if (customAlertDialogState.value.code.isNotEmpty()) {
                    LearnerCodeDialog().LearnerCodeDialogScreen(
                        code = customAlertDialogState.value.code,
                        onClickCancel = { resetDialogState(customAlertDialogState) },
                        onClickRequest = {
                            memberService.getMemberCode(getSharedPreferences("Member", Context.MODE_PRIVATE).getString("Jwt", "")!!)
                        },
                        onClickCopy = {code->
                            copyToClipboard(code)
                        }
                    )
                }
            }
        }
    }

    private fun resetDialogState(state: MutableState<CustomAlertDialogState>) {
        state.value = CustomAlertDialogState()
    }
    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Learner Code", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "복사되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onGetMemberCodeSuccess(response: GeneralResponse<String>) {
        customAlertDialogState.value = CustomAlertDialogState(
            code = response.result!!,
            onClickCancel = {
                resetDialogState(customAlertDialogState)
            }
        )
    }

    override fun onGetMemberCodeFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetMemberInfoSuccess(response: GeneralResponse<MemberGetResponse>) {
        Log.d("Info" , response.result.toString())
        memberInfo.value = response.result!!
    }

    override fun onGetMemberInfoFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }
}