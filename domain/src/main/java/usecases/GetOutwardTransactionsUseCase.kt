package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.Validator

class GetOutwardTransactionsUseCase(
    private val repository: TransactionRepository,
    private val aliasValidator: Validator<Alias>
) : TransactionUseCase<Alias?, List<Transaction>> {

    override fun execute(alias: Alias?): List<Transaction> {
        aliasValidator.validate(alias)
        return repository.getOutwardTransactions(alias)
    }
}