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

public class JdbcTransactionRepository implements TransactionRepository {

    private static int idCounter = 1; // Static counter to generate unique IDs
    private final String tableName;
    @Getter
    private final DatabaseConnection databaseConnection;
    public JdbcTransactionRepository(String tableName, DatabaseConnection databaseConnection) {
        this.tableName = tableName;
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void add(Transaction transaction) {
        int newId = idCounter++;
        transaction.setTransactionId(newId);
        String insertSQL = String.format(
                "INSERT INTO %s (transaction_id, debtor, debtor_type, creditor, creditor_type, amount, currency, purpose, timestamp)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName);
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, transaction.getTransactionId());
            pstmt.setString(2, transaction.getDebtor().value());
            pstmt.setString(3, transaction.getDebtor().type().name());
            pstmt.setString(4, transaction.getCreditor().value());
            pstmt.setString(5, transaction.getCreditor().type().name());
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
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
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


    @Override
    public List<Transaction> getAllTransactions() {
        return getSqlTransactions(null);
    }

    public List<Transaction> getInwardTransactions(Alias alias) {
        return getSqlTransactions(alias, true);
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        return getSqlTransactions(alias, false);
    }

    private List<Transaction> getSqlTransactions(Alias alias) {
        return getSqlTransactions(alias, null);
    }

    private List<Transaction> getSqlTransactions(Alias alias, Boolean isInward) {
        List<Transaction> transactions = new ArrayList<>();
        String querySQL;
        if (alias == null) {
            querySQL = String.format("SELECT * FROM %s", tableName);
        } else if (isInward != null && isInward) {
            querySQL = String.format("SELECT * FROM %s WHERE creditor = ?", tableName);
        } else {
            querySQL = String.format("SELECT * FROM %s WHERE debtor = ?", tableName);
        }

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(querySQL)) {
            if (alias != null) {
                pstmt.setString(1, alias.value());
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    Alias debtor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("debtor_type")), rs.getString("debtor"));
                    Alias creditor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("creditor_type")), rs.getString("creditor"));
                    double amount = rs.getDouble("amount");
                    Date timestamp = new Date(rs.getTimestamp("timestamp").getTime());
                    Transaction.Currency currency = Transaction.Currency.valueOf(rs.getString("currency"));
                    String purpose = rs.getString("purpose");
                    Transaction transaction = new Transaction(debtor, creditor, amount, currency, purpose, timestamp);
                    transaction.setTransactionId(transactionId);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void printAllTransactions() {
        getAllTransactions().forEach(System.out::println);
    }

}
