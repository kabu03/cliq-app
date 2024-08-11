import models.Alias;
import models.Transaction;
import repositories.DatabaseTransactionRepository;
import repositories.MemoryTransactionRepository;
import services.TransactionService;
import validators.AliasValidator;
import validators.TransactionValidator;
import validators.Validator;

class Main {
    public static void main(String[] args) {
        DatabaseTransactionRepository record = new DatabaseTransactionRepository("transactions"); // implements repositories.TransactionRepository.
        Validator<Transaction> transactionValidator = new TransactionValidator(); // validators.TransactionValidator implements validators.Validator<models.Transaction>.
        Validator<Alias> aliasValidator = new AliasValidator(); // validators.AliasValidator implements validators.Validator<models.Alias>.
        TransactionService service = new TransactionService(record, transactionValidator, aliasValidator);

        /*
        services.TransactionService uses repositories.TransactionRepository, validators.Validator<models.Transaction>, and validators.Validator<models.Alias>.
        services.TransactionService follows the Dependency Inversion Principle.
         */


        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Samir the Seeziologist");
        Transaction t = new Transaction(karam, jebreen, 3000, Transaction.Currency.USD, "Payment", null);
        Transaction t1 = new Transaction(karam, jebreen, 5000, Transaction.Currency.JOD, "Cuss", null);
        record.add(t);
        record.remove(t.getTransactionId());
        System.out.println(t.getTransactionId());
        record.printAllTransactions();
    }
}

