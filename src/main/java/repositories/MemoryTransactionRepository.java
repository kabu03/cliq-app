package repositories;

import models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MemoryTransactionRepository implements TransactionRepository {
    public List<Transaction> transactions;
    private static int idCounter = 1;

    public MemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
        int newId = idCounter++;
        transaction.setId(newId);
    }

    public void remove(int transactionID) {
        transactions.remove(transactionID);
    }


}
