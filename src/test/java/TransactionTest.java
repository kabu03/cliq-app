import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionTest {
    private MemoryTransactionRepository repository;
    private TransactionService service;
    private Validator<Transaction> transactionValidator;
    private Validator<Alias> aliasValidator;

    @BeforeEach
    public void setUp() {
        this.repository = new MemoryTransactionRepository();
        this.transactionValidator = new TransactionValidator();
        this.aliasValidator = new AliasValidator();
        this.service = new TransactionService(repository, transactionValidator, aliasValidator);
    }

    @Test
    public void testAdd() {

        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen");

        Transaction t1 = new Transaction(karam, jebreen, 100.0, Transaction.Currency.USD, "Payment");
        Transaction t2 = new Transaction(jebreen, karam, 200.0, Transaction.Currency.EUR, "Invoice");
        repository.add(t1);
        repository.add(t2);
        assertEquals(2, repository.transactions.size());
        assertTrue(repository.transactions.contains(t1));
        assertTrue(repository.transactions.contains(t2));
    }

    @Test
    public void testInwardTransactions() {
        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen");

        Transaction t1 = new Transaction(karam, jebreen, 100.0, Transaction.Currency.USD, "Payment");
        Transaction t2 = new Transaction(jebreen, karam, 200.0, Transaction.Currency.EUR, "Invoice");
        Transaction t3 = new Transaction(karam, jebreen, 500.0, Transaction.Currency.EUR, "Invoice");
        repository.add(t1);
        repository.add(t2);
        repository.add(t3);
        assertEquals(1, service.getInwardTransactions(karam).size());
        assertEquals(t2, service.getInwardTransactions(karam).get(0));

        assertEquals(2, service.getInwardTransactions(jebreen).size());
        assertTrue(service.getInwardTransactions(jebreen).contains(t1));
        assertTrue(service.getInwardTransactions(jebreen).contains(t3));
    }

    @Test
    public void testInvalidAlias() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            aliasValidator.validate(new Alias(Alias.AllowedAliasTypes.IBAN, "invalid_iban"));
            // 12 CHARACTER IBAN
        });
        assertEquals("IBAN must be 30 characters long." + "The IBAN you inserted is " + 12 + " characters long.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            aliasValidator.validate(new Alias(Alias.AllowedAliasTypes.PHONE_NUMBER, "invalid_phone")); // 13 CHARACTER PHONE NUMBER
        });
        assertEquals("Phone number must be 10 digits." + "The phone number you inserted is " + 13 + " digits long.", exception.getMessage());
    }

    @Test
    public void testInvalidTransaction() {
        TransactionValidator validator = new TransactionValidator();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), -100.0, Transaction.Currency.USD, "Payment");
            validator.validate(transaction);
        });
        assertEquals("Amount must be greater than 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, Transaction.Currency.USD, null);
            validator.validate(transaction);
        });
        assertEquals("Purpose cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, null, "Payment");
            validator.validate(transaction);
        });
        assertEquals("Currency cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), null, 100.0, Transaction.Currency.USD, "Payment");
            validator.validate(transaction);
        });
        assertEquals("Creditor cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(null, new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, Transaction.Currency.USD, "Payment");
            validator.validate(transaction);
        });
        assertEquals("Debtor cannot be null.", exception.getMessage());
    }

}
