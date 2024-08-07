package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthCommonRequest(
    val name :String,
    val email :String,
    val birthDate : String
)

