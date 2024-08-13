package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity

import java.sql.Timestamp


data class SymbolRecord (
    val symbolId:Long,
    val touchOrder:Long,
    val touchTime : Timestamp,
)
