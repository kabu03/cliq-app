import models.Alias;
import models.Transaction;
import repositories.MemoryTransactionRepository;
import services.TransactionService;
import validators.AliasValidator;
import validators.TransactionValidator;
import validators.Validator;

class Main {
    public static void main(String[] args) {
        MemoryTransactionRepository record = new MemoryTransactionRepository(); // repositories.MemoryTransactionRepository implements repositories.TransactionRepository.
        Validator<Transaction> transactionValidator = new TransactionValidator(); // validators.TransactionValidator implements validators.Validator<models.Transaction>.
        Validator<Alias> aliasValidator = new AliasValidator(); // validators.AliasValidator implements validators.Validator<models.Alias>.
        TransactionService service = new TransactionService(record, transactionValidator, aliasValidator);
        // services.TransactionService uses repositories.TransactionRepository, validators.Validator<models.Transaction>, and validators.Validator<models.Alias>.
        // services.TransactionService follows the Dependency Inversion Principle.

        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Jebreen");
        Transaction t = new Transaction(karam, jebreen, 100.0, Transaction.Currency.USD, "Payment");
        Transaction t1 = new Transaction(karam, jebreen, 200.0, Transaction.Currency.USD, "Payment");

        System.out.println("Adding transaction t");
        service.addTransaction(t); // The service will validate everything using the validator instance and then add the transaction to the repository.
        record.printAllTransactions();

        System.out.println("Adding transaction t1");
        service.addTransaction(t1);
        record.printAllTransactions();

        System.out.println("Getting all transactions");
        System.out.println(service.getAllTransactions());
    }
}

