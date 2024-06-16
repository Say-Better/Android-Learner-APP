package gdsc.solutionchallenge.saybetter.saybetterlearner.model.remote.dto

enum class DataModelType {
    StartAudioCall, StartVideoCall, Offer, Answer, IceCandidates, EndCall
}

data class DataModel(
    val sender : String? = null,
    val target : String,
    val type : DataModelType,
    val data : String? = null,
    val timeStamp : Long = System.currentTimeMillis()
)

//DataModel은 60초 동안 유효함
fun DataModel.isValid() : Boolean {
    return System.currentTimeMillis() - this.timeStamp < 60000
}
