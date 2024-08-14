package models;

import models.Alias;
import models.Transaction;

import java.util.List;

public interface TransactionRepository {

    boolean add(Transaction transaction);

    boolean remove(int transactionID);

    List<Transaction> getTransactionsByAlias(Alias alias);

    List<Transaction> getInwardTransactions(Alias alias);

    List<Transaction> getOutwardTransactions(Alias alias);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(int id);

}




