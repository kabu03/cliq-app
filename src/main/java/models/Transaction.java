package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode
@ToString
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
        return "models.Transaction Details {" +
                "debtor=" + debtor.value() +
                ", creditor=" + creditor.value() +
                ", amount=" + amount +
                ", currency=" + currency +
                ", purpose='" + purpose + '\'' +
                ", timestamp=" + timestamp +
                " }";
    }

    public enum Currency {
        JOD, USD, EUR, GBP, HUF// Add other allowed currencies
    }
}