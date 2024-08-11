package services;

import models.Alias;
import models.Transaction;
import repositories.TransactionRepository;
import validators.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    void removeTransaction(int transactionId);
    List<Transaction> getTransactionsByAlias(Alias alias);
    List<Transaction> getInwardTransactions(Alias alias);
    List<Transaction> getOutwardTransactions(Alias alias);
    List<Transaction> getAllTransactions();


    }


