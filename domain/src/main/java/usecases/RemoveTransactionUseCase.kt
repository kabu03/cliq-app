package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.Validator

class RemoveTransactionUseCase constructor(
    private val repository: TransactionRepository,
    private val transactionValidator: Validator<Transaction>,
    private val aliasValidator: Validator<Alias>
) : TransactionUseCase<Int, Boolean> {
    override fun execute(transactionID: Int): Boolean {
        return repository.remove(transactionID)
    }
}