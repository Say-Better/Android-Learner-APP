package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGetResponse

interface MemberGetView {
    fun onGetMemberInfoSuccess(response: MemberGetResponse)
    fun onGetMemberInfoFailure(isSuccess: Boolean, code: String, message: String)
}