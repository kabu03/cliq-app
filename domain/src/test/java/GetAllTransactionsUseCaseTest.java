import kotlin.Unit;
import models.Transaction;
import usecases.GetAllTransactionsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.TransactionRepository;
import validators.AliasValidator;
import validators.TransactionValidator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetAllTransactionsUseCaseTest {

    private TransactionRepository transactionRepository;
    private GetAllTransactionsUseCase getAllTransactionsUseCase;
    private AliasValidator aliasValidator;
    private TransactionValidator transactionValidator;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        aliasValidator = Mockito.mock(AliasValidator.class);
        transactionValidator = Mockito.mock(TransactionValidator.class);
        getAllTransactionsUseCase = new GetAllTransactionsUseCase(transactionRepository, transactionValidator, aliasValidator);
    }

    @Test
    void shouldReturnAllTransactions() {
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(), new Transaction());

        when(transactionRepository.getAllTransactions()).thenReturn(mockTransactions);

        List<Transaction> result = getAllTransactionsUseCase.execute(Unit.INSTANCE);

        assertEquals(2, result.size());
    }
}
