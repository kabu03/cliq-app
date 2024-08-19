package repositories;

import lombok.Getter;
import models.Alias;
import models.Transaction;
import models.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class JdbcTransactionRepository implements TransactionRepository {

    private final String tableName;
    private final DatabaseConnection databaseConnection;

    // Injected dependencies for testing
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public JdbcTransactionRepository(String tableName, DatabaseConnection databaseConnection) {
        this.tableName = tableName;
        this.databaseConnection = databaseConnection;
    }

    // Constructor for dependency injection
    public JdbcTransactionRepository(String tableName, DatabaseConnection databaseConnection, Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        this.tableName = tableName;
        this.databaseConnection = databaseConnection;
        this.connection = connection;
        this.preparedStatement = preparedStatement;
        this.resultSet = resultSet;
    }

    public List<Transaction> getAllTransactions() {
        return getSqlTransactions(null, null);
    }

    @Override
    public Transaction getTransactionById(int id) {
        String querySQL = String.format("SELECT * FROM %s WHERE transaction_id = ?", tableName);
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    Alias debtor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("debtor_type")), rs.getString("debtor"));
                    Alias creditor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("creditor_type")), rs.getString("creditor"));
                    double amount = rs.getDouble("amount");
                    Date timestamp = new Date(rs.getTimestamp("timestamp").getTime());
                    String currency = rs.getString("currency");
                    String purpose = rs.getString("purpose");

                    Transaction transaction = new Transaction(debtor, creditor, amount, currency, purpose, timestamp);
                    transaction.setId(transactionId);
                    return transaction;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean add(Transaction transaction) {
        String insertSQL = String.format(
                "INSERT INTO %s (transaction_id, debtor, debtor_type, creditor, creditor_type, amount, currency, purpose, timestamp)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName);
        try (Connection conn = connection != null ? connection : databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, transaction.getId());
            pstmt.setString(2, transaction.getDebtor().getValue());
            pstmt.setString(3, transaction.getDebtor().getType().name());
            pstmt.setString(4, transaction.getCreditor().getValue());
            pstmt.setString(5, transaction.getCreditor().getType().name());
            pstmt.setDouble(6, transaction.getAmount());
            pstmt.setString(7, transaction.getCurrency());
            pstmt.setString(8, transaction.getPurpose());
            pstmt.setTimestamp(9, new java.sql.Timestamp(transaction.getTimestamp().getTime()));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Returns true if the transaction was added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if there was an exception
        }
    }


    @Override
    public boolean remove(int transactionId) {
        String deleteSQL = String.format("DELETE FROM %s WHERE transaction_id = ?", tableName);
        try (Connection conn = connection != null ? connection : databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, transactionId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Returns true if a transaction was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if there was an exception
        }
    }


    public List<Transaction> getTransactionsByAlias(Alias alias) {
        return getSqlTransactions(alias, null);
    }


    public List<Transaction> getInwardTransactions(Alias alias) {
        return getSqlTransactions(alias, true);
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        return getSqlTransactions(alias, false);
    }

    private List<Transaction> getSqlTransactions(Alias alias, Boolean isInward) {
        List<Transaction> transactions = new ArrayList<>();
        String querySQL;

        if (alias == null) {
            // Get all transactions if alias is null
            querySQL = String.format("SELECT * FROM %s", getTableName());
        } else if (isInward == null) {
            // Get both inward and outward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE debtor = ? OR creditor = ?", getTableName());
        } else if (isInward) {
            // Get only inward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE creditor = ?", getTableName());
        } else {
            // Get only outward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE debtor = ?", getTableName());
        }

        try (Connection conn = getConnection() != null ? getConnection() : getDatabaseConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            if (alias != null) {
                pstmt.setString(1, alias.getValue());
                if (isInward == null) {
                    pstmt.setString(2, alias.getValue());
                }
            }
            try (ResultSet rs = getResultSet() != null ? getResultSet() : pstmt.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    Alias debtor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("debtor_type")), rs.getString("debtor"));
                    Alias creditor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("creditor_type")), rs.getString("creditor"));
                    double amount = rs.getDouble("amount");
                    Date timestamp = new Date(rs.getTimestamp("timestamp").getTime());
                    String currency = rs.getString("currency");
                    String purpose = rs.getString("purpose");
                    Transaction transaction = new Transaction(debtor, creditor, amount, currency, purpose, timestamp);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public void printAllTransactions() {
        getAllTransactions().forEach(System.out::println);
    }

}
