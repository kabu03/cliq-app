import java.util.Date;

public class Transaction {
    private final Alias debtor;
    private final Alias creditor;
    private final double amount;
    private final Currency currency;
    private final String purpose;
    private final Date timestamp;

    public Transaction(Alias debtor, Alias creditor, double amount, Currency currency, String purpose) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.currency = currency;
        this.purpose = purpose;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "Transaction Details {" +
                "debtor=" + debtor.value() +
                ", creditor=" + creditor.value() +
                ", amount=" + amount +
                ", currency=" + currency +
                ", purpose='" + purpose + '\'' +
                ", timestamp=" + timestamp +
                " }";
    }

    public Alias getDebtor() {
        return debtor;
    }

    public Alias getCreditor() {
        return creditor;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getPurpose() {
        return purpose;
    }

    public Date getTimestamp() {
        return timestamp;
    }


    public enum Currency {
        JOD, USD, EUR, GBP, HUF// Add other allowed currencies
    }
}
