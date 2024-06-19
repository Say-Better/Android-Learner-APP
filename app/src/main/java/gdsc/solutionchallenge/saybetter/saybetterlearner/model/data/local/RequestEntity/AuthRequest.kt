package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val identityToken :String
)

