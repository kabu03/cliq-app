import java.util.List;

public interface TransactionRepository {
    void add(Transaction transaction);

    void remove(Transaction transaction);

    List<Transaction> getAllTransactions();

    void printAllTransactions();

}
