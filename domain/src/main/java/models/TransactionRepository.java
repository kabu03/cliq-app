package models;

import models.Alias;
import models.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactionRepository {

    boolean add(Transaction transaction);

    boolean remove(int transactionID);

    List<Transaction> getTransactionsByAlias(Alias alias);

    List<Transaction> getInwardTransactions(Alias alias);

    List<Transaction> getOutwardTransactions(Alias alias);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(int id);

}




