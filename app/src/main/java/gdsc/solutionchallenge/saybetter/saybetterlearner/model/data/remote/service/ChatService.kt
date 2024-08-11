package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service

import android.util.Log
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.chat.ChatRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.chat.ChatResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit.ChatRetrofitInterface
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.chat.ChatBotView
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.module.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ChatService {
    private lateinit var chatBotView: ChatBotView

    fun setChatBotView(chatBotView: ChatBotView) {
        this.chatBotView = chatBotView
    }

    fun getChatBot(
        accessToken : String,
        sentence : String
    ) {
        val chatBotService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatBotService.getChatBotAnswer("Bearer ${accessToken}", ChatRequest(sentence)).enqueue(object : Callback<GeneralResponse<ChatResponse>>{
            override fun onResponse(
                call: Call<GeneralResponse<ChatResponse>>,
                response: Response<GeneralResponse<ChatResponse>>
            ) {
                if (response.isSuccessful) {
                    val resp: GeneralResponse<ChatResponse>? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "SUCCESS_200" -> chatBotView.onGetChatSuccess(resp)       //변경
                            else -> chatBotView.onGetChatFailure(
                                resp.isSuccess,
                                resp.code,
                                resp.message
                            )
                        }
                    } else {
                        Log.e("getChatBot", "Response body is null")
                    }
                } else {
                    Log.e("getChatBot", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GeneralResponse<ChatResponse>>, t: Throwable) {
                Log.d("getChatBot", t.toString())
            }

        })


    }
}