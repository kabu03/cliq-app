import models.Alias;
import models.Transaction;
import usecases.GetTransactionsByAliasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import models.TransactionRepository;
import validators.AliasValidator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetTransactionsByAliasUseCaseTest {

    private TransactionRepository transactionRepository;
    private GetTransactionsByAliasUseCase getTransactionsByAliasUseCase;
    private AliasValidator aliasValidator;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        aliasValidator = Mockito.mock(AliasValidator.class);
        getTransactionsByAliasUseCase = new GetTransactionsByAliasUseCase(transactionRepository, aliasValidator);
    }

    @Test
    void shouldReturnTransactionsByAlias() {
        Alias alias = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "alias");
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(), new Transaction());

        when(transactionRepository.getTransactionsByAlias(alias)).thenReturn(mockTransactions);

        List<Transaction> result = getTransactionsByAliasUseCase.execute(alias);

        assertEquals(2, result.size());
    }
}
