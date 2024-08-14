//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import domain.models.Alias;
//import domain.models.Transaction;
//import application.repositories.JdbcTransactionRepository;
//import application.repositories.DatabaseConnection;
//import org.mockito.Mockito;
//
//import java.sql.*;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class JdbcTest {
//
//    private JdbcTransactionRepository repository;
//    private Connection mockConnection;
//    private PreparedStatement mockPreparedStatement;
//    private ResultSet mockResultSet;
//    private JdbcTransactionService service;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        // Mock the repositories.DatabaseConnection and related objects
//        repositories.DatabaseConnection mockDatabaseConnection = Mockito.mock(repositories.DatabaseConnection.class);
//        mockConnection = Mockito.mock(Connection.class);
//        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
//        mockResultSet = Mockito.mock(ResultSet.class);
//
//        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
//
//        // Initialize the repository with mocked objects
//        repository = new JdbcTransactionRepository("transactionsTest", mockDatabaseConnection, mockConnection, mockPreparedStatement, mockResultSet);
//
//        // Initialize the service with the repository
//        service = new JdbcTransactionService(repository, null, null); // You may replace null with actual validators if needed
//    }
//
//
//    @Test
//    public void testAddTransaction() throws SQLException {
//        // Arrange
//        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
//        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");
//        Transaction transaction = new Transaction(debtor, creditor, 1000.0, Transaction.Currency.USD, "Test payment", new Date());
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//
//        // Act
//        repository.add(transaction);
//
//        // Assert
//        verify(mockPreparedStatement, times(1)).executeUpdate();
//    }
//
//    @Test
//    public void testGetAllTransactions() throws SQLException {
//        // Arrange
//        when(mockConnection.prepareStatement("SELECT * FROM transactionsTest")).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//
//        // Simulate two rows in the ResultSet
//        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(mockResultSet.getInt("transaction_id")).thenReturn(1, 2);
//        when(mockResultSet.getString("debtor")).thenReturn("DebtorAlias1", "DebtorAlias2");
//        when(mockResultSet.getString("creditor")).thenReturn("CreditorAlias1", "CreditorAlias2");
//        when(mockResultSet.getDouble("amount")).thenReturn(1000.0, 2000.0);
//        when(mockResultSet.getString("currency")).thenReturn("USD", "EUR");
//        when(mockResultSet.getString("purpose")).thenReturn("Payment 1", "Payment 2");
//        when(mockResultSet.getString("debtor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getString("creditor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getTimestamp("timestamp")).thenReturn(new Timestamp(new Date().getTime()));
//
//        // Act
//        List<Transaction> transactions = service.getAllTransactions();
//
//        // Assert
//        assertEquals(2, transactions.size());
//        assertEquals("DebtorAlias1", transactions.get(0).getDebtor().getValue());
//        assertEquals("CreditorAlias1", transactions.get(0).getCreditor().getValue());
//        assertEquals(1000.0, transactions.get(0).getAmount());
//        assertEquals(Transaction.Currency.USD, transactions.get(0).getCurrency());
//        assertEquals("Payment 1", transactions.get(0).getPurpose());
//    }
//
//    @Test
//    public void testGetInwardTransactions() throws SQLException {
//        // Arrange
//        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//
//        // Simulate two rows in the ResultSet
//        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(mockResultSet.getInt("transaction_id")).thenReturn(1, 2);
//        when(mockResultSet.getString("creditor")).thenReturn("CreditorAlias", "CreditorAlias");
//        when(mockResultSet.getString("debtor")).thenReturn("DebtorAlias", "OtherDebtor");
//        when(mockResultSet.getString("debtor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getString("creditor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getDouble("amount")).thenReturn(1000.0, 2000.0);
//        when(mockResultSet.getString("currency")).thenReturn("USD", "USD");
//        when(mockResultSet.getString("purpose")).thenReturn("Payment 1", "Payment 2");
//        when(mockResultSet.getTimestamp("timestamp")).thenReturn(new Timestamp(new Date().getTime()));
//
//        // Act
//        List<Transaction> inwardTransactions = service.getInwardTransactions(creditor);
//
//        // Assert
//        assertEquals(2, inwardTransactions.size());
//        assertEquals("CreditorAlias", inwardTransactions.get(0).getCreditor().getValue());
//        assertEquals("CreditorAlias", inwardTransactions.get(1).getCreditor().getValue());
//    }
//
//    @Test
//    public void testGetOutwardTransactions() throws SQLException {
//        // Arrange
//        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//
//        // Simulate two rows in the ResultSet
//        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(mockResultSet.getInt("transaction_id")).thenReturn(1, 2);
//        when(mockResultSet.getString("debtor")).thenReturn("DebtorAlias", "DebtorAlias");
//        when(mockResultSet.getString("creditor")).thenReturn("CreditorAlias1", "CreditorAlias2");
//        when(mockResultSet.getString("debtor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getString("creditor_type")).thenReturn("ALPHANUMERIC", "ALPHANUMERIC");
//        when(mockResultSet.getDouble("amount")).thenReturn(1000.0, 2000.0);
//        when(mockResultSet.getString("currency")).thenReturn("USD", "USD");
//        when(mockResultSet.getString("purpose")).thenReturn("Payment 1", "Payment 2");
//        when(mockResultSet.getTimestamp("timestamp")).thenReturn(new Timestamp(new Date().getTime()));
//
//        // Act
//        List<Transaction> outwardTransactions = service.getOutwardTransactions(debtor);
//
//        // Assert
//        assertEquals(2, outwardTransactions.size());
//        assertEquals("DebtorAlias", outwardTransactions.get(0).getDebtor().getValue());
//        assertEquals("DebtorAlias", outwardTransactions.get(1).getDebtor().getValue());
//    }
//
//
//    // Add tests for getInwardTransactions, getOutwardTransactions, etc.
//
//    @Test
//    public void testRemoveTransactionById() throws SQLException {
//        // Arrange
//        Alias debtor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "DebtorAlias");
//        Alias creditor = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "CreditorAlias");
//        Transaction transaction1 = new Transaction(debtor, creditor, 1000.0, Transaction.Currency.USD, "Payment 1", new Date());
//        Transaction transaction2 = new Transaction(debtor, creditor, 2000.0, Transaction.Currency.USD, "Payment 2", new Date());
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
//
//        repository.add(transaction1);
//        repository.add(transaction2);
//
//        // Act: Remove the first transaction
//        repository.remove(transaction1.getId());
//
//        // Assert: Ensure remove was called
//        verify(mockPreparedStatement, times(3)).executeUpdate();
//    }
//}
