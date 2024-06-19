package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service

import android.util.Log
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.AuthRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.Auth.AuthResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.AuthView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit.AuthRetrofitInterface
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.Module.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var authView : AuthView

    fun setAuthView(authView : AuthView) {
        this.authView = authView
    }

    fun postLogin(authRequest: AuthRequest) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)
        loginService.postLogin(authRequest, "LEARNER", "GOOGLE").enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val resp: AuthResponse? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "MEMBER2005" -> authView.onPostLoginSuccess(resp)       //변경
                            else -> authView.onPostLoginFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("SIGNUP-SUCCESS", "Response body is null")
                    }
                } else {
                    Log.e("SIGNUP-SUCCESS", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP-FAILURE", t.toString())
            }
        })
    }
}