import models.Transaction;
import usecases.GetTransactionByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.TransactionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GetTransactionByIdUseCaseTest {

    private TransactionRepository transactionRepository;
    private GetTransactionByIdUseCase getTransactionByIdUseCase;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        getTransactionByIdUseCase = new GetTransactionByIdUseCase(transactionRepository);
    }

    @Test
    void shouldReturnTransactionById() {
        int transactionId = 5;
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(transactionId);  // Explicitly set the id

        when(transactionRepository.getTransactionById(transactionId)).thenReturn(mockTransaction);

        Transaction result = getTransactionByIdUseCase.execute(transactionId);

        assertEquals(5, result.getId());
    }

}
