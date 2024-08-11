package repositories;

import models.Transaction;

import java.util.List;

public interface TransactionRepository {

    void add(Transaction transaction);

    void remove(int transactionID);

}
