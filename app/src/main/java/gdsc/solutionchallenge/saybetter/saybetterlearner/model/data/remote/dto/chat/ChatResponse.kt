package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.chat

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("firstAnswer") val firstAnswer:String,
    @SerializedName("secondAnswer") val secondAnswer:String,
)
