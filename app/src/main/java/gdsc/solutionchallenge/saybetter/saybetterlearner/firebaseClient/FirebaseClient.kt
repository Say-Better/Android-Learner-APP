package gdsc.solutionchallenge.saybetter.saybetterlearner.firebaseClient

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.DataModel
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.FirebaseFieldNames.LATEST_EVENT
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.FirebaseFieldNames.STATUS
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.MyEventListener
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.UserStatus
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef : DatabaseReference,
    private val gson : Gson
) {

    private var currentUserid : String? = null
    private fun setUserid(userid : String) {
        this.currentUserid = userid
    }

    fun testAccess(){
        dbRef.setValue("hello firebase")
            .addOnCompleteListener {
                Log.d("FirebaseClient", "DB Access Success")
            }
            .addOnFailureListener {
                Log.d("FirebaseClient", "DB Access Failed")
            }
    }

    fun login(userid : String, done : (Boolean, String?) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : MyEventListener() {
            override fun onDataChange(snapshot: DataSnapshot) {
                //입력한 id가 이미 존재하는 경우
                if(snapshot.hasChild(userid)) {
                    dbRef.child(userid).child(STATUS).setValue(UserStatus.ONLINE)
                        .addOnCompleteListener {
                            setUserid(userid)
                            done(true, null)
                        }.addOnFailureListener {
                            done(false, "${it.message}")
                        }
                }
                else {
                    dbRef.child(userid).child(STATUS).setValue(UserStatus.ONLINE)
                        .addOnCompleteListener {
                            setUserid(userid)
                            done(true, null)
                        }.addOnFailureListener {
                            done(false, "${it.message}")
                        }
                }
            }
        })
    }

    //자신의 Latest Event 항목의 변화를 청취
    fun subscribeForLatestEvent(listener : Listener){
        try{
            dbRef.child(currentUserid!!).child(LATEST_EVENT).addValueEventListener(
                object : MyEventListener() {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        super.onDataChange(snapshot)
                        val event  = try {
                            gson.fromJson(snapshot.value.toString(), DataModel::class.java)
                        }catch (e : Exception) {
                            e.printStackTrace()
                            null
                        }

                        event?.let {
                            listener.onLatestEventReceived(it)
                        }
                    }
                }
            )
        }catch (e : Exception) {
            e.printStackTrace()
            Log.d("Firebase Listener", "Something Wrong")
        }
    }

    fun sendMessageToOtherClient(message : DataModel, success : (Boolean) -> Unit){
        val convertedMessage = gson.toJson(message.copy(sender = currentUserid))
        dbRef.child(message.target).child(LATEST_EVENT).setValue(convertedMessage)
            .addOnCompleteListener {
                success(true)
            }.addOnFailureListener {
                success(false)
            }
    }

    interface Listener {
        fun onLatestEventReceived(event : DataModel)
    }

}