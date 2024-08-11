package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.auth.AuthCommonRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.chat.ChatRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth.AuthCommonResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.chat.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatRetrofitInterface {
    @POST("/api/chatting")
    fun getChatBotAnswer(@Header("Authorization") Authorization: String,
                         @Body chatRequest: ChatRequest): Call<GeneralResponse<ChatResponse>>

}