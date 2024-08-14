package requests

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionRequest constructor(
    var debtorAliasType: String,
    var debtorAliasValue: String,
    var creditorAliasType: String,
    var creditorAliasValue: String,
    var amount: Double,
    var currency: String,
    var purpose: String,
    override var comments: String? = null
) : Request {

    // Timestamp is automatically set to the current time when the object is created
    val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

}
