package gdsc.solutionchallenge.saybetter.saybetterlearner.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.menu.MenuActivity
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.videocall.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleSignInHelper(
    private val context: Context,
    private val onSignInSuccess: (String?) -> Unit // Callback to handle success
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val credentialManager: CredentialManager = CredentialManager.create(context)


    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .setAutoSelectEnabled(true)
        .build()

    private val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    fun login() {
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch(e : Exception) {
                handleFailure(e)
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            // Passkey credential
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                // Handle the response JSON as needed
                onSignInSuccess(responseJson)
            }
            // Password credential
            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
                // Handle username and password as needed
                onSignInSuccess(username)
            }
            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        // Handle the ID token as needed
                        onSignInSuccess(idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid Google ID token response", e)
                    }
                } else {
                    Log.e(TAG, "Unexpected type of custom credential")
                }
            }
            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun handleFailure(exception: Exception) {
        Log.e(TAG, "Credential request failed", exception)
        // Optionally, you can call onSignInSuccess with null or handle the failure differently
    }
}