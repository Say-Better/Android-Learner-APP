package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service

import android.util.Log
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.auth.AuthCommonRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth.AuthCommonResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.auth.AuthView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit.AuthRetrofitInterface
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.module.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var authView : AuthView

    fun setAuthView(authView : AuthView) {
        this.authView = authView
    }

    fun postCommonLogin(authCommonRequest: AuthCommonRequest) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)
        loginService.postLoginCommon(authCommonRequest).enqueue(object : Callback<GeneralResponse<AuthCommonResponse>> {
            override fun onResponse(call: Call<GeneralResponse<AuthCommonResponse>>, response: Response<GeneralResponse<AuthCommonResponse>>) {
                Log.d("LOGIN API", "")
                if (response.isSuccessful) {
                    val resp: GeneralResponse<AuthCommonResponse>? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "SUCCESS_200" -> authView.onPostLoginSuccess(resp)       //변경
                            else -> authView.onPostLoginFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("SIGNUP-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("SIGNUP-SUCCESS", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<GeneralResponse<AuthCommonResponse>>, t: Throwable) {
                Log.d("SIGNUP-FAILURE", t.toString())
            }
        })
    }
}