package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse

interface MemberCodeView {
    fun onGetMemberCodeSuccess(response: GeneralResponse<String>)
    fun onGetMemberCodeFailure(isSuccess: Boolean, code: String, message: String)
}