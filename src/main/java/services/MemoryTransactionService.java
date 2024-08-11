package services;

import models.Alias;
import models.Transaction;
import repositories.MemoryTransactionRepository;
import repositories.TransactionRepository;
import validators.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MemoryTransactionService implements TransactionService {
    private final MemoryTransactionRepository repository;
    private final Validator<Transaction> transactionValidator;
    private final Validator<Alias> aliasValidator;

    public MemoryTransactionService(MemoryTransactionRepository repository, Validator<Transaction> transactionValidator, Validator<Alias> aliasValidator) {
        this.repository = repository;
        this.transactionValidator = transactionValidator;
        this.aliasValidator = aliasValidator;
    }

    public void addTransaction(Transaction transaction) {
        aliasValidator.validate(transaction.getCreditor());
        aliasValidator.validate(transaction.getDebtor());
        transactionValidator.validate(transaction);
        repository.add(transaction);
    }

    public void removeTransaction(int transactionID) {
        for (Transaction t : getAllTransactions()) {
            if (Objects.equals(t.getId(), transactionID)) {
                repository.remove(transactionID);
                return;
            }
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
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        List<Transaction> outwardTransactions = getAllTransactions().stream().filter(transaction -> transaction.getDebtor().equals(alias)).collect(Collectors.toList());
        System.out.println("Here are all the transactions where " + alias.getValue() + " is the debtor:");
        outwardTransactions.forEach(System.out::println);
        return outwardTransactions;
    }

    public List<Transaction> filterTransactions(Date startDate, Date endDate, double minAmount, double maxAmount, Transaction.Currency currency) {
        return getAllTransactions().stream().filter(transaction -> !transaction.getTimestamp().before(startDate) && !transaction.getTimestamp().after(endDate)).filter(transaction -> transaction.getAmount() >= minAmount && transaction.getAmount() <= maxAmount).filter(transaction -> transaction.getCurrency().equals(currency)).collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(repository.transactions);
    }
}