package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class GoogleSignInHelper(
    private val context: Context,
    private val onSignInSuccess: (String?, String?) -> Unit // Callback to handle success
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val credentialManager: CredentialManager = CredentialManager.create(context)


    // Google Sign-In with GetSignInWithGoogleOption
    private val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(context.getString(R.string.default_web_client_id))
        .build()

    private val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    // Google 로그인 요청 실행
    fun signIn() {
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                handleFailure(e)
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        onSignInSuccess(idToken, getEmailFromIdToken(idToken).toString())
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                }
                else {
                    // Catch any unrecognized credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun handleFailure(exception: Exception) {
        Log.e(TAG, "Credential request failed", exception)
        // Optionally, you can call onSignInSuccess with null or handle the failure differently
    }
    fun getEmailFromIdToken(idToken: String): String? {
        try {
            // Split the JWT to get the payload part
            val parts = idToken.split(".")
            if (parts.size == 3) {
                val payload = parts[1]
                // Base64 decode the payload
                val decodedPayload = String(Base64.decode(payload, Base64.URL_SAFE))
                // Convert the payload into a JSON object
                val jsonObject = JSONObject(decodedPayload)
                // Extract the email claim
                return jsonObject.getString("email")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error decoding id token", e)
        }
        return null
    }
}