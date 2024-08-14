package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.Validator


class GetAllTransactionsUseCase constructor(
    private val repository: TransactionRepository,
    private val transactionValidator: Validator<Transaction>,
    private val aliasValidator: Validator<Alias>
) : TransactionUseCase<Unit, List<Transaction>> {

    override fun execute(input: Unit): List<Transaction> {
        return repository.getAllTransactions()
    }
}