package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity

data class ChatMessage(
    val isUser: Boolean,
    val message: String,
    val symbol : Int
)
