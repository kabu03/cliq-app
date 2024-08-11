package repositories;

import lombok.Getter;
import models.Alias;
import models.Transaction;
import services.DatabaseConnection;

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

    @Override
    public void add(Transaction transaction) {
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
            pstmt.setString(7, transaction.getCurrency().name());
            pstmt.setString(8, transaction.getPurpose());
            pstmt.setTimestamp(9, new java.sql.Timestamp(transaction.getTimestamp().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int transactionId) {
        String deleteSQL = String.format("DELETE FROM %s WHERE transaction_id = ?", tableName);
        try (Connection conn = connection != null ? connection : databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, transactionId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No transaction was found to delete.");
            } else {
                System.out.println("Transaction deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
