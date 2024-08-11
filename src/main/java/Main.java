import models.Alias;
import models.Transaction;
import repositories.HibernateTransactionRepository;
import services.HibernateTransactionService;
import services.TransactionService;
import validators.AliasValidator;
import validators.TransactionValidator;
import validators.Validator;

class Main {
    public static void main(String[] args) {
        HibernateTransactionRepository record = new HibernateTransactionRepository(); // implements repositories.TransactionRepository.
        Validator<Transaction> transactionValidator = new TransactionValidator(); // validators.TransactionValidator implements validators.Validator<models.Transaction>.
        Validator<Alias> aliasValidator = new AliasValidator(); // validators.AliasValidator implements validators.Validator<models.Alias>.
        HibernateTransactionService service = new HibernateTransactionService(record, transactionValidator, aliasValidator);

        /*
        services.TransactionService uses repositories.TransactionRepository, validators.Validator<models.Transaction>, and validators.Validator<models.Alias>.
        services.TransactionService follows the Dependency Inversion Principle.
         */

        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Samir");
        Transaction t = new Transaction(karam, jebreen, 3000, Transaction.Currency.USD, "Payment", null);
        Transaction t1 = new Transaction(karam, jebreen, 5000, Transaction.Currency.JOD, "Food", null);
        service.addTransaction(t);
        service.addTransaction(t1);
        service.printAllTransactions();
    }
}

