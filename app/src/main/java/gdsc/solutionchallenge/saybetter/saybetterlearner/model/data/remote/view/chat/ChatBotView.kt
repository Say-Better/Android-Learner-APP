package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.chat

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.chat.ChatResponse

interface ChatBotView {
    fun onGetChatSuccess(response: GeneralResponse<ChatResponse>)
    fun onGetChatFailure(isSuccess: Boolean, code: String, message: String)
}