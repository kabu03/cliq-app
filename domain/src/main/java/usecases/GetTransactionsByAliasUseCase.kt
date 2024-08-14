package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository

class GetTransactionsByAliasUseCase
constructor (repository: TransactionRepository) : TransactionUseCase<Alias, List<Transaction>> {
    private val repository: TransactionRepository = repository

    override fun execute(alias: Alias): List<Transaction> {
        return repository.getTransactionsByAlias(alias)
    }
}