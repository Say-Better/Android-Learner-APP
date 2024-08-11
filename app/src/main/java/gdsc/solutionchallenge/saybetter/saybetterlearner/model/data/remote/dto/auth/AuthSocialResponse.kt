package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthSocialResponse(
    @SerializedName("identityToken") val identityToken:String
)
