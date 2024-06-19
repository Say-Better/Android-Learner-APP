package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.AuthRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.Auth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthRetrofitInterface {
    @POST("api/auth/login/{appType}/{socialType}")
    fun postLogin(@Body authRequest: AuthRequest,
                  @Path("appType") appType: String,
                  @Path("socialType") socialType: String): Call<AuthResponse>

}