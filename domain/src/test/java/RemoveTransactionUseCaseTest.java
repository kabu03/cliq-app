import usecases.RemoveTransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.TransactionRepository;
import validators.AliasValidator;
import validators.TransactionValidator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RemoveTransactionUseCaseTest {

    private TransactionRepository transactionRepository;
    private RemoveTransactionUseCase removeTransactionUseCase;
    private AliasValidator aliasValidator;
    private TransactionValidator transactionValidator;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        aliasValidator = Mockito.mock(AliasValidator.class);
        transactionValidator = Mockito.mock(TransactionValidator.class);
        removeTransactionUseCase = new RemoveTransactionUseCase(transactionRepository, transactionValidator, aliasValidator);
    }

    @Test
    void shouldRemoveTransactionSuccessfully() {
        int transactionId = 1;

        when(transactionRepository.remove(transactionId)).thenReturn(true);

        boolean result = removeTransactionUseCase.execute(transactionId);

        assertTrue(result);
    }
}
