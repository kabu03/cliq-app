package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.Validator

class GetInwardTransactionsUseCase(
    private val repository: TransactionRepository,
    private val transactionValidator: Validator<Transaction>,
    private val aliasValidator: Validator<Alias>
) : TransactionUseCase<Alias?, List<Transaction>> {

    override fun execute(alias: Alias?): List<Transaction> {
        return repository.getInwardTransactions(alias)
    }
}