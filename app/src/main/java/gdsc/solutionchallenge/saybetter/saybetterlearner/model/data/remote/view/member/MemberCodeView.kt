package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse

interface MemberCodeView {
    fun onGetMemberCodeSuccess(response: MemberGeneralResponse)
    fun onGetMemberCodeFailure(isSuccess: Boolean, code: String, message: String)
}