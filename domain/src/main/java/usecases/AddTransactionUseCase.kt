package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.Validator

class AddTransactionUseCase constructor
    (
    private val repository: TransactionRepository,
    private val transactionValidator: Validator<Transaction>,
    private val aliasValidator: Validator<Alias>
) : TransactionUseCase<Transaction, Boolean> {

    override fun execute(transaction: Transaction): Boolean {
        aliasValidator.validate(transaction.creditor)
        aliasValidator.validate(transaction.debtor)
        transactionValidator.validate(transaction)
        return repository.add(transaction)
    }
}