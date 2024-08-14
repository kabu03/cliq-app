package requests

class TransactionIdRequest constructor(
    var transactionId: Int,
    override var comments: String? = null
) : Request {
}