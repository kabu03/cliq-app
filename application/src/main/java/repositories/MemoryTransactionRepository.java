package repositories;

import exceptions.TransactionNotFoundException;
import models.Alias;
import models.Transaction;
import models.TransactionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryTransactionRepository implements TransactionRepository {
    private static int idCounter = 1;
    public List<Transaction> transactions;

    public MemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public boolean add(Transaction transaction) {
        int newId = idCounter++;
        transaction.setId(newId);
        return transactions.add(transaction);
    }

    public Transaction getTransactionById(int transactionID) {
        try {
            for (Transaction transaction : transactions) {
                if (transaction.getId() == transactionID) {
                    return transaction;
                }
            }
            throw new TransactionNotFoundException("Transaction with ID " + transactionID + " was not found");
        } catch (TransactionNotFoundException e) {
            // Handle the exception gracefully
            System.err.println(e.getMessage());
            return null;
        }
    }


    public boolean remove(int transactionID) {
        Transaction transactionToRemove = null;

        for (Transaction transaction : transactions) {
            if (transaction.getId() == transactionID) {
                transactionToRemove = transaction;
                break;  // Break the loop once the transaction is found
            }
        }

        if (transactionToRemove != null) {
            transactions.remove(transactionToRemove);
            return true;
        } else {
            System.err.println("Transaction with ID " + transactionID + " was not found");
            return false;
        }
    }


    public List<Transaction> getTransactionsByAlias(Alias alias) {
        List<Transaction> filteredTransactionsByAlias = getAllTransactions().stream().filter(transaction -> (transaction.getCreditor().equals(alias) || transaction.getDebtor().equals(alias))).collect(Collectors.toList());
        if (filteredTransactionsByAlias.isEmpty()) {
            System.out.println("No transactions found for " + alias.getValue());
        } else {
            System.out.println("Here is the transaction history for " + alias.getValue() + ":");
            filteredTransactionsByAlias.forEach(System.out::println);
        }
        return filteredTransactionsByAlias;
    }

    public List<Transaction> getInwardTransactions(Alias alias) {
        List<Transaction> inwardTransactions = getAllTransactions().stream().filter(transaction -> transaction.getCreditor().equals(alias)).collect(Collectors.toList());
        if (inwardTransactions.isEmpty()) {
            System.out.println("No transactions found where " + alias.getValue() + " is the creditor");
        } else {
            System.out.println("Here are all the transactions where " + alias.getValue() + " is the creditor:");
            inwardTransactions.forEach(System.out::println);
        }
        return inwardTransactions;
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        List<Transaction> outwardTransactions = getAllTransactions().stream().filter(transaction -> transaction.getDebtor().equals(alias)).collect(Collectors.toList());
        if (outwardTransactions.isEmpty()) {
            System.out.println("No transactions found where " + alias.getValue() + " is the debtor");
        } else {
            System.out.println("Here are all the transactions where " + alias.getValue() + " is the debtor:");
            outwardTransactions.forEach(System.out::println);
        }
        return outwardTransactions;
    }

    public List<Transaction> filterTransactions(Date startDate, Date endDate, double minAmount, double maxAmount, String currency) {
        return getAllTransactions().stream().filter(transaction -> !transaction.getTimestamp().before(startDate) && !transaction.getTimestamp().after(endDate)).filter(transaction -> transaction.getAmount() >= minAmount && transaction.getAmount() <= maxAmount).filter(transaction -> transaction.getCurrency().equals(currency)).collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }


}
