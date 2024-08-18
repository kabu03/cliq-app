package responses

import models.Transaction

data class Response(
    val status: String,
    val transactionId: Int? = null,
    val transaction: Transaction? = null, // Optional, only if needed
    val transactionList: List<Transaction>? = null // Optional, only if needed
) {
    constructor(status: String) : this(status, null, null, null)

    override fun toString(): String {
        val result = StringBuilder("Response(status='$status'")

        transactionId?.let {
            result.append(", transactionId=$transactionId")
        }

        transaction?.let {
            result.append(", transaction=$transaction")
        }

        transactionList?.let {
            if (transactionList.isNotEmpty()) {
                result.append(", transactionList=")
                transactionList.forEach { transaction ->
                    result.append("\n\t$transaction")
                }
            } else {
                result.append(", transactionList is empty")
            }
        } ?: run {
            result.append(", no list of transactions was sent.")
        }

        result.append(")")
        return result.toString()
    }
}
