package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGetResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MemberRetrofitInterface {
    @Multipart
    @POST("/api/member/learner/info")
    fun postMemberInfo(@Header("Authorization") Authorization: String,
                       @Part file : MultipartBody.Part,
                       @Part("dto") dto : RequestBody
    ): Call<GeneralResponse<String>>


    @GET("/api/member/learner/info")
    fun getMemberInfo(@Header("Authorization") Authorization: String): Call<GeneralResponse<MemberGetResponse>>

    @GET("/api/member/connect/code")
    fun getMemberCode(@Header("Authorization") Authorization: String): Call<GeneralResponse<String>>

}