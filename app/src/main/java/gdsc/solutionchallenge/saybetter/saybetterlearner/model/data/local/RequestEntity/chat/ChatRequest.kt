package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest (
    private val sentence:String
)