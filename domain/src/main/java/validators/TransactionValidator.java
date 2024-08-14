package validators;

import models.Transaction;

public class TransactionValidator implements Validator<Transaction> {
    public void validate(Transaction transaction) {
        if (transaction.getDebtor() == null) {
            throw new IllegalArgumentException("Debtor cannot be null.");
        }
        if (transaction.getCreditor() == null) {
            throw new IllegalArgumentException("Creditor cannot be null.");
        }
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        if (transaction.getCurrency() == null) {
            throw new IllegalArgumentException("Currency cannot be null.");
        }
        if (transaction.getPurpose() == null) {
            throw new IllegalArgumentException("Purpose cannot be null.");
        }
    }
}
