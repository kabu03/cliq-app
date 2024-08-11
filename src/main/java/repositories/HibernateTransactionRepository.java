package repositories;

import lombok.Getter;
import models.Alias;
import models.Transaction;
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
    public void add(Transaction transaction) {
//        int newId = idCounter++;
//        transaction.setTransactionId(newId);
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.persist(transaction);
            tx.commit();
        }
    }

    @Override
    public void remove(int transactionID) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction tx = session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, transactionID);
            if (transaction != null) {
                session.remove(transaction);
            }
            tx.commit();
        }
    }


}
