package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.auth

import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.auth.AuthCommonResponse

interface AuthView {
    fun onPostLoginSuccess(response: AuthCommonResponse)
    fun onPostLoginFailure(isSuccess: Boolean, code: String, message: String)
}