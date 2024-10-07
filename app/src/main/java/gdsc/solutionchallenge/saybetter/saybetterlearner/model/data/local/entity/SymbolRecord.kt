package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity

import java.sql.Timestamp


data class SymbolRecord (
    val symbolId:Int,
    val touchOrder:Long,
    val touchTime : Timestamp,
)
