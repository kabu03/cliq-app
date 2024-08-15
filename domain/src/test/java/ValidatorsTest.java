import models.Alias;
import models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.AliasValidator;
import validators.TransactionValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorsTest {
    private AliasValidator aliasValidator;
    private TransactionValidator transactionValidator;

    @BeforeEach
    public void setUp() {
        aliasValidator = new AliasValidator();
        transactionValidator = new TransactionValidator();
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
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), -100.0, Transaction.Currency.USD, "Payment", null);
            validator.validate(transaction);
        });
        assertEquals("Amount must be greater than 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, Transaction.Currency.USD, null, null);
            validator.validate(transaction);
        });
        assertEquals("Purpose cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, null, "Payment", null);
            validator.validate(transaction);
        });
        assertEquals("Currency cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam"), null, 100.0, Transaction.Currency.USD, "Payment", null);
            validator.validate(transaction);
        });
        assertEquals("Creditor cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(null, new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen"), 100.0, Transaction.Currency.USD, "Payment", null);
            validator.validate(transaction);
        });
        assertEquals("Debtor cannot be null.", exception.getMessage());
    }
}