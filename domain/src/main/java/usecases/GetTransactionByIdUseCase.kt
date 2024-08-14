package usecases

import models.Transaction
import models.TransactionRepository

class GetTransactionByIdUseCase constructor (
    private val repository: TransactionRepository
) : TransactionUseCase<Int, Transaction> {
    override fun execute(input: Int): Transaction {
        return repository.getTransactionById(input)
    }

}