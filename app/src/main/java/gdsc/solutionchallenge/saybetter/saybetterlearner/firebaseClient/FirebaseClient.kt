package gdsc.solutionchallenge.saybetter.saybetterlearner.firebaseClient

import android.util.Log
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef : DatabaseReference
) {

    fun testAccess(){
        dbRef.setValue("hello firebase")
            .addOnCompleteListener {
                Log.d("FirebaseClient", "DB Access Success")
            }
            .addOnFailureListener {
                Log.d("FirebaseClient", "DB Access Failed")
            }
    }

}