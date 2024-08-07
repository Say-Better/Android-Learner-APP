package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse

interface MemberInfoView {
    fun onPostMemberInfoSuccess(response: MemberGeneralResponse)
    fun onPostMemberInfoFailure(isSuccess: Boolean, code: String, message: String)
}