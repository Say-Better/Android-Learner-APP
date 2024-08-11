package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse

interface MemberInfoView {
    fun onPostMemberInfoSuccess(response: GeneralResponse<String>)
    fun onPostMemberInfoFailure(isSuccess: Boolean, code: String, message: String)
}