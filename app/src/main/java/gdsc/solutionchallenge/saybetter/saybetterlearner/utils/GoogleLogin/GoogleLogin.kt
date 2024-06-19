package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity

class GoogleSignInHelper(
    private val context: Context,
    private val activity: Activity,
    private val googleAuthLauncher: ActivityResultLauncher<Intent>,
    private val onSignInSuccess: (String?) -> Unit // 콜백 추가
) {
    var googleIdToken: String? = null
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
        Log.d("GoogleSignInOptions", googleSignInOptions.serverClientId.toString())
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }

    fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            googleIdToken = account?.idToken
            Log.d("GoogleToken", googleIdToken.toString())
            onSignInSuccess(googleIdToken)
        } catch (e: ApiException) {
            Log.e(ContentValues.TAG, "Google login failed", e)
        }
    }
    fun loginWithGoogle() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }
}
