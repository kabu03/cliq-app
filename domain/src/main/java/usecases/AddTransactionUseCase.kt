package usecases

import models.Alias
import models.Transaction
import models.TransactionRepository
import validators.CurrencyValidator
import validators.Validator

class AddTransactionUseCase constructor
    (
    private val repository: TransactionRepository,
    private val transactionValidator: Validator<Transaction>,
    private val aliasValidator: Validator<Alias>,
    private val currencyValidator: CurrencyValidator
) : TransactionUseCase<Transaction, Boolean> {

    override fun execute(transaction: Transaction): Boolean {
        aliasValidator.validate(transaction.creditor)
        aliasValidator.validate(transaction.debtor)
        transactionValidator.validate(transaction)
        currencyValidator.validate(transaction.currency)
        return repository.add(transaction)
    }
}