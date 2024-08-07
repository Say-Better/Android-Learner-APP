package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member

import com.google.gson.annotations.SerializedName

data class MemberGeneralResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)
