package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthSocialRequest(
    val identityToken :String
)

