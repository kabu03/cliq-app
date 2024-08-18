package requests

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionRequest @JsonCreator constructor(
    @JsonProperty("debtorAliasType") val debtorAliasType: String,
    @JsonProperty("debtorAliasValue") val debtorAliasValue: String,
    @JsonProperty("creditorAliasType") val creditorAliasType: String,
    @JsonProperty("creditorAliasValue") val creditorAliasValue: String,
    @JsonProperty("amount") val amount: Double,
    @JsonProperty("currency") val currency: String,
    @JsonProperty("purpose") val purpose: String,
    @JsonProperty("comments") val comments: String? = null
) {
    val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
