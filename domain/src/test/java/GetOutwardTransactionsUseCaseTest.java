import models.Alias;
import models.Transaction;
import usecases.GetOutwardTransactionsUseCase;
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

class GetOutwardTransactionsUseCaseTest {

    private TransactionRepository transactionRepository;
    private GetOutwardTransactionsUseCase getOutwardTransactionsUseCase;
    private AliasValidator aliasValidator;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        aliasValidator = Mockito.mock(AliasValidator.class);
        getOutwardTransactionsUseCase = new GetOutwardTransactionsUseCase(transactionRepository, aliasValidator);
    }

    @Test
    void shouldReturnOutwardTransactions() {
        Alias creditorAlias = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "creditorAlias");
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(), new Transaction());

        when(transactionRepository.getOutwardTransactions(creditorAlias)).thenReturn(mockTransactions);

        List<Transaction> result = getOutwardTransactionsUseCase.execute(creditorAlias);

        assertEquals(2, result.size());
    }
}
