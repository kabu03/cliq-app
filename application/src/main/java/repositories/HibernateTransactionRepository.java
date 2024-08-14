package repositories;

import models.Alias;
import models.Transaction;
import lombok.Getter;
import models.TransactionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Getter
public class HibernateTransactionRepository implements TransactionRepository {

    private final SessionFactory sessionFactory;
//    private static int idCounter = 1; // Static counter to generate unique IDs

    public HibernateTransactionRepository() {
        // Configure Hibernate and build the session factory
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Transaction.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public boolean add(Transaction transaction) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.persist(transaction);
            tx.commit();
            return true; // Returns true if the transaction was persisted successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Returns false if there was an exception
        }
    }


    @Override
    public boolean remove(int transactionID) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, transactionID);
            if (transaction != null) {
                session.remove(transaction);
                tx.commit();
                return true; // Returns true if the transaction was removed successfully
            } else {
                tx.commit();
                return false; // Returns false if the transaction was not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Returns false if there was an exception
        }
    }


    public List<Transaction> getAllTransactions() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from Transaction", Transaction.class).list();
        }
    }

    @Override
    public Transaction getTransactionById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Transaction.class, id);
        }
    }


    public List<Transaction> getInwardTransactions(Alias alias) {
        try (Session session = getSessionFactory().openSession()) {
            String hql = "from Transaction t where t.creditor.type = :creditorType and t.creditor.value = :creditorValue";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("creditorType", alias.getType())
                    .setParameter("creditorValue", alias.getValue())
                    .list();
        }
    }

    public List<Transaction> getOutwardTransactions(Alias alias) {
        try (Session session = getSessionFactory().openSession()) {
            String hql = "from Transaction t where t.debtor.type = :debtorType and t.debtor.value = :debtorValue";
            return session.createQuery(hql, Transaction.class)
                    .setParameter("debtorType", alias.getType())
                    .setParameter("debtorValue", alias.getValue())
                    .list();
        }
    }

    public List<Transaction> getTransactionsByAlias(Alias alias) {
        try (Session session = getSessionFactory().openSession()) {
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
