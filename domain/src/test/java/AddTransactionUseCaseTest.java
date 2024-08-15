import models.Alias;
import models.Transaction;
import usecases.AddTransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.TransactionRepository;
import validators.AliasValidator;
import validators.TransactionValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AddTransactionUseCaseTest {

    private TransactionRepository transactionRepository;
    private TransactionValidator transactionValidator;
    private AliasValidator aliasValidator;
    private AddTransactionUseCase addTransactionUseCase;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionValidator = Mockito.mock(TransactionValidator.class);
        aliasValidator = Mockito.mock(AliasValidator.class);
        addTransactionUseCase = new AddTransactionUseCase(transactionRepository, transactionValidator, aliasValidator);
    }

    @Test
    void shouldAddTransactionSuccessfully() {
        Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "KARAM"),
                new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "TEST"),
                        53, Transaction.Currency.HUF,
                "test", null); // initialize with valid data

        doNothing().when(transactionValidator).validate(transaction);
        when(transactionRepository.add(transaction)).thenReturn(true);

        boolean result = addTransactionUseCase.execute(transaction);

        assertTrue(result);
    }

    @Test
    void shouldFailToAddTransactionWhenValidationFails() {
        Transaction transaction = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "KARAM"),
                new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "TEST"),
                -200, Transaction.Currency.HUF,
                "test", null);

        boolean result = addTransactionUseCase.execute(transaction);

        assertFalse(result);
    }
}
