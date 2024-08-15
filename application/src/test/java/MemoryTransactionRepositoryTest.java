import models.Alias;
import models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.MemoryTransactionRepository;

import static org.junit.jupiter.api.Assertions.*;


public class MemoryTransactionRepositoryTest {
    private MemoryTransactionRepository repository;

    @BeforeEach
    public void setUp() {
        this.repository = new MemoryTransactionRepository();
    }

    @Test
    public void testAdd() {

        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "jebreen");

        Transaction t1 = new Transaction(karam, jebreen, 100.0, "USD", "Payment", null);
        Transaction t2 = new Transaction(jebreen, karam, 200.0, "EUR", "Invoice", null);
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

        Transaction t1 = new Transaction(karam, jebreen, 100.0, "USD", "Payment", null);
        Transaction t2 = new Transaction(jebreen, karam, 200.0, "EUR", "Invoice", null);
        Transaction t3 = new Transaction(karam, jebreen, 500.0, "EUR", "Invoice", null);
        repository.add(t1);
        repository.add(t2);
        repository.add(t3);
        assertEquals(1, repository.getInwardTransactions(karam).size());
        assertEquals(t2, repository.getInwardTransactions(karam).get(0));

        assertEquals(2, repository.getInwardTransactions(jebreen).size());
        assertTrue(repository.getInwardTransactions(jebreen).contains(t1));
        assertTrue(repository.getInwardTransactions(jebreen).contains(t3));
    }

}
