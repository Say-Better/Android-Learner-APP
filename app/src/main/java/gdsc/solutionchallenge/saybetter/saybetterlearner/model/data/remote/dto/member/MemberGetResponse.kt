package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member

import com.google.gson.annotations.SerializedName

data class MemberGetResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result (
        @SerializedName("name") val name: String,
        @SerializedName("age") val age: Int,
        @SerializedName("gender") val gender: String,
        @SerializedName("imgUrl") val imgUrl: String
    )
}
