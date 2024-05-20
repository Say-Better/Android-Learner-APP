package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity

data class ChatMessage(
    val isUser: Boolean,
    val timestamp:String,
    val message: String,
    val symbol : Int
)
