package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member

import com.google.gson.annotations.SerializedName

data class MemberGetResponse(
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("imgUrl") val imgUrl: String
)
