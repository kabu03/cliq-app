package repositories;

import models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MemoryTransactionRepository implements TransactionRepository {
    public List<Transaction> transactions;

    public MemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public void remove(Transaction transaction) {
        transactions.remove(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public void printAllTransactions() {
        transactions.forEach(System.out::println);
    }

}
