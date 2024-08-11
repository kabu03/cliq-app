// APPLY HIBERNATE
// Tests without connection, using Mocking and Stubbing
// concurrency, reflection API


import org.junit.jupiter.api.*;
import models.Alias;
import models.Transaction;
import repositories.JdbcTransactionRepository;
import services.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class JdbcTest {

    private JdbcTransactionRepository repository;

    @BeforeEach
    public void setUp() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        repository = new JdbcTransactionRepository("transactionsTest", databaseConnection);
        try (Connection connection = databaseConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE transactionsTest RESTART IDENTITY CASCADE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTransaction() {
        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");
        Transaction transaction = new Transaction(debtor, creditor, 1000.0, Transaction.Currency.USD, "Test payment", new Date());

        repository.add(transaction);

        List<Transaction> transactions = repository.getAllTransactions();
        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals("DebtorAlias", transactions.get(0).getDebtor().value());
        Assertions.assertEquals("CreditorAlias", transactions.get(0).getCreditor().value());
        Assertions.assertEquals(1000.0, transactions.get(0).getAmount());
        Assertions.assertEquals(Transaction.Currency.USD, transactions.get(0).getCurrency());
        Assertions.assertEquals("Test payment", transactions.get(0).getPurpose());
    }

    @Test
    public void testGetAllTransactions() {
        Alias debtor1 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias1");
        Alias creditor1 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias1");
        Transaction transaction1 = new Transaction(debtor1, creditor1, 1000.0, Transaction.Currency.USD, "Payment 1", new Date());

        Alias debtor2 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias2");
        Alias creditor2 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias2");
        Transaction transaction2 = new Transaction(debtor2, creditor2, 2000.0, Transaction.Currency.EUR, "Payment 2", new Date());

        repository.add(transaction1);
        repository.add(transaction2);

        List<Transaction> transactions = repository.getAllTransactions();
        Assertions.assertEquals(2, transactions.size());
        Assertions.assertEquals("DebtorAlias1", transactions.get(0).getDebtor().value());
        Assertions.assertEquals("CreditorAlias1", transactions.get(0).getCreditor().value());
        Assertions.assertEquals(1000.0, transactions.get(0).getAmount());
        Assertions.assertEquals(Transaction.Currency.USD, transactions.get(0).getCurrency());
        Assertions.assertEquals("Payment 1", transactions.get(0).getPurpose());
    }

    @Test
    public void testGetInwardTransactions() {
        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");

        Transaction transaction1 = new Transaction(debtor, creditor, 1000.0, Transaction.Currency.USD, "Payment 1", new Date());
        Transaction transaction2 = new Transaction(new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "OtherDebtor"), creditor, 2000.0, Transaction.Currency.EUR, "Payment 2", new Date());

        repository.add(transaction1);
        repository.add(transaction2);

        List<Transaction> inwardTransactions = repository.getInwardTransactions(creditor);
        Assertions.assertEquals(2, inwardTransactions.size());
        Assertions.assertEquals("CreditorAlias", inwardTransactions.get(0).getCreditor().value());
        Assertions.assertEquals("CreditorAlias", inwardTransactions.get(1).getCreditor().value());
    }

    @Test
    public void testGetOutwardTransactions() {
        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
        Alias creditor1 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias1");
        Alias creditor2 = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias2");

        Transaction transaction1 = new Transaction(debtor, creditor1, 1000.0, Transaction.Currency.USD, "Payment 1", new Date());
        Transaction transaction2 = new Transaction(debtor, creditor2, 2000.0, Transaction.Currency.EUR, "Payment 2", new Date());

        repository.add(transaction1);
        repository.add(transaction2);

        List<Transaction> outwardTransactions = repository.getOutwardTransactions(debtor);
        Assertions.assertEquals(2, outwardTransactions.size());
        Assertions.assertEquals("DebtorAlias", outwardTransactions.get(0).getDebtor().value());
        Assertions.assertEquals("DebtorAlias", outwardTransactions.get(1).getDebtor().value());
    }

    @Test
    public void testRemoveTransactionById() {
        // Arrange
        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");
        Transaction transaction1 = new Transaction(debtor, creditor, 1000.0, Transaction.Currency.USD, "Payment 1", new Date());
        Transaction transaction2 = new Transaction(debtor, creditor, 2000.0, Transaction.Currency.USD, "Payment 2", new Date());

        // Add transactions to the repository
        repository.add(transaction1);
        repository.add(transaction2);

        // Assert both transactions are added
        List<Transaction> transactionsBeforeRemove = repository.getAllTransactions();
        Assertions.assertEquals(2, transactionsBeforeRemove.size(), "Both transactions should be added.");

        // Act: Remove the first transaction
        repository.remove(transaction1.getTransactionId());

        // Assert: The first transaction should be removed
        List<Transaction> transactionsAfterRemove = repository.getAllTransactions();
        Assertions.assertEquals(1, transactionsAfterRemove.size(), "There should be one transaction remaining.");
        Assertions.assertEquals(transaction2.getTransactionId(), transactionsAfterRemove.get(0).getTransactionId(), "The remaining transaction should be the second one.");
    }



    @AfterEach
    public void tearDown() {
        // Clean up after tests if necessary
        try (Connection connection = repository.getDatabaseConnection().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE transactionsTest RESTART IDENTITY CASCADE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
