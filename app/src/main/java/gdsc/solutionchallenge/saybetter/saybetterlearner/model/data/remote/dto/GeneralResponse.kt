package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeneralResponse<T>(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: T?
)
