package services;

import models.Alias;
import models.Transaction;
import org.hibernate.Session;
import repositories.HibernateTransactionRepository;
import validators.Validator;

import java.util.List;

public class HibernateTransactionService implements TransactionService {
    private final HibernateTransactionRepository repository;
    private final Validator<Transaction> transactionValidator;
    private final Validator<Alias> aliasValidator;

    public HibernateTransactionService(HibernateTransactionRepository repository, Validator<Transaction> transactionValidator, Validator<Alias> aliasValidator) {
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

    public void removeTransaction(int transactionId) {
        repository.remove(transactionId);
    }

    public List<Transaction> getAllTransactions() {
        try (Session session = repository.getSessionFactory().openSession()) {
            return session.createQuery("from Transaction", Transaction.class).list();
        }
    }

    public List<Transaction> getInwardTransactions(Alias alias) {
        try (Session session = repository.getSessionFactory().openSession()) {
            String hql = "from Transaction t where t.creditor.type = :creditorType and t.creditor.value = :creditorValue";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("creditorType", alias.getType())
                    .setParameter("creditorValue", alias.getValue())
                    .list();
        }
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        try (Session session = repository.getSessionFactory().openSession()) {
            String hql = "from Transaction t where t.debtor.type = :debtorType and t.debtor.value = :debtorValue";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("debtorType", alias.getType())
                    .setParameter("debtorValue", alias.getValue())
                    .list();
        }
    }
    public List<Transaction> getTransactionsByAlias(Alias alias) {
        try (Session session = repository.getSessionFactory().openSession()) {
            String hql = "from Transaction t where t.debtor.type = :type and t.debtor.value = :value or t.creditor.type = :type and t.creditor.value = :value";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("type", alias.getType())
                    .setParameter("value", alias.getValue())
                    .list();
        }
    }
    public void printAllTransactions() {
        getAllTransactions().forEach(System.out::println);
    }
}
