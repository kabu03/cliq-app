package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.AliasValidator

class GetTransactionsByAliasUseCase
constructor (repository: TransactionRepository, aliasValidator: AliasValidator) : TransactionUseCase<Alias, List<Transaction>> {
    private val repository: TransactionRepository = repository
    private val aliasValidator: AliasValidator = aliasValidator

    override fun execute(alias: Alias): List<Transaction> {
        aliasValidator.validate(alias)
        return repository.getTransactionsByAlias(alias)
    }
}