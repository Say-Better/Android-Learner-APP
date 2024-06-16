package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.Auth.AuthResponse

interface AuthView {
    fun onPostLoginSuccess(response: AuthResponse)
    fun onPostLoginFailure(isSuccess: Boolean, code: String, message: String)
}