package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.auth.AuthCommonRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth.AuthCommonResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/auth/login/LEARNER/common")
    fun postLoginCommon(@Body authCommonRequest: AuthCommonRequest): Call<AuthCommonResponse>

}