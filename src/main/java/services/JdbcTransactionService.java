package services;

import models.Alias;
import models.Transaction;
import repositories.JdbcTransactionRepository;
import repositories.TransactionRepository;
import validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcTransactionService implements TransactionService {
    private final JdbcTransactionRepository repository;
    private final Validator<Transaction> transactionValidator;
    private final Validator<Alias> aliasValidator;

    public JdbcTransactionService(JdbcTransactionRepository repository, Validator<Transaction> transactionValidator, Validator<Alias> aliasValidator) {
        this.repository = repository;
        this.transactionValidator = transactionValidator;
        this.aliasValidator = aliasValidator;
    }
    @Override
    public List<Transaction> getAllTransactions() {
        return getSqlTransactions(null, null);
    }

    public void addTransaction(Transaction transaction) {
        aliasValidator.validate(transaction.getCreditor());
        aliasValidator.validate(transaction.getDebtor());
        transactionValidator.validate(transaction);
        repository.add(transaction);
    }

    public void removeTransaction(int transactionId) {
        repository.remove(transactionId);
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
            querySQL = String.format("SELECT * FROM %s", repository.getTableName());
        } else if (isInward == null) {
            // Get both inward and outward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE debtor = ? OR creditor = ?", repository.getTableName());
        } else if (isInward) {
            // Get only inward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE creditor = ?", repository.getTableName());
        } else {
            // Get only outward transactions by alias
            querySQL = String.format("SELECT * FROM %s WHERE debtor = ?", repository.getTableName());
        }

        try (Connection conn = repository.getConnection() != null ? repository.getConnection() : repository.getDatabaseConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            if (alias != null) {
                pstmt.setString(1, alias.getValue());
                if (isInward == null) {
                    pstmt.setString(2, alias.getValue());
                }
            }
            try (ResultSet rs = repository.getResultSet() != null ? repository.getResultSet() : pstmt.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    Alias debtor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("debtor_type")), rs.getString("debtor"));
                    Alias creditor = new Alias(Alias.AllowedAliasTypes.valueOf(rs.getString("creditor_type")), rs.getString("creditor"));
                    double amount = rs.getDouble("amount");
                    Date timestamp = new Date(rs.getTimestamp("timestamp").getTime());
                    Transaction.Currency currency = Transaction.Currency.valueOf(rs.getString("currency"));
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
