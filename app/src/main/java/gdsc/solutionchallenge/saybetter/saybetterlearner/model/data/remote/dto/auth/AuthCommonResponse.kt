package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthCommonResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("memberId") val memberId:Int,
        @SerializedName("accessToken") val accessToken:String,
        @SerializedName("refreshToken") val refreshToken:String,
        @SerializedName("needMemberInfo") val needMemberInfo:Boolean,
    )
}
