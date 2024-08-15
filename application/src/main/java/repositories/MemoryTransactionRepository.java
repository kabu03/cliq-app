package repositories;

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
        for (Transaction transaction : transactions) {
            if (transaction.getId() == transactionID) {
                return transaction;
            }
        }
        return null;
    }


    public boolean remove(int transactionID) {
        Transaction transactionToRemove = null;
        for (Transaction transaction : transactions) {
            if (transaction.getId() == transactionID) {
                transactionToRemove = transaction;
                break;
            }
        }

        if (transactionToRemove != null) {
            return transactions.remove(transactionToRemove);
        } else {
            return false;
        }
    }


    public List<Transaction> getTransactionsByAlias(Alias alias) {
        List<Transaction> filteredTransactionsByAlias = getAllTransactions().stream().filter(transaction -> (transaction.getCreditor().equals(alias) || transaction.getDebtor().equals(alias))).collect(Collectors.toList());
        System.out.println("Here is the transaction history for " + alias.getValue() + ":");
        filteredTransactionsByAlias.forEach(System.out::println);
        return filteredTransactionsByAlias;
    }

    public List<Transaction> getInwardTransactions(Alias alias) {
        List<Transaction> inwardTransactions = getAllTransactions().stream().filter(transaction -> transaction.getCreditor().equals(alias)).collect(Collectors.toList());
        System.out.println("Here are all the transactions where " + alias.getValue() + " is the creditor:");
        inwardTransactions.forEach(System.out::println);
        return inwardTransactions;
        //return repository.getInwardTransactions(alias);
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        List<Transaction> outwardTransactions = getAllTransactions().stream().filter(transaction -> transaction.getDebtor().equals(alias)).collect(Collectors.toList());
        System.out.println("Here are all the transactions where " + alias.getValue() + " is the debtor:");
        outwardTransactions.forEach(System.out::println);
        return outwardTransactions;
    }

    public List<Transaction> filterTransactions(Date startDate, Date endDate, double minAmount, double maxAmount, String currency) {
        return getAllTransactions().stream().filter(transaction -> !transaction.getTimestamp().before(startDate) && !transaction.getTimestamp().after(endDate)).filter(transaction -> transaction.getAmount() >= minAmount && transaction.getAmount() <= maxAmount).filter(transaction -> transaction.getCurrency().equals(currency)).collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }


}
