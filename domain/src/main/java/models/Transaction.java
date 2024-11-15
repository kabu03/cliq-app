package models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@Table(name = "transactions")
@Entity
public class Transaction {
    @Column(name = "amount")
    private final double amount;
    @Column(name = "currency")
    private final String currency;
    @Column(name = "purpose")
    private final String purpose;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private final Date timestamp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // Database primary key
    //    @Column(name = "transaction_id", unique = true, nullable = false)
//    private int transactionId;  // Code-handled ID
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "debtor_type")),
            @AttributeOverride(name = "value", column = @Column(name = "debtor_value"))
    })
    private Alias debtor;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "creditor_type")),
            @AttributeOverride(name = "value", column = @Column(name = "creditor_value"))
    })
    private Alias creditor;


    public Transaction(Alias debtor, Alias creditor, double amount, String currency, String purpose, Date timestamp) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.currency = currency;
        this.purpose = purpose;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, Date::new);
    }

    public Transaction() {
        this.debtor = new Alias();
        this.creditor = new Alias();
        this.amount = 0;
        this.currency = "JOD";
        this.purpose = "";
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "domain.models.Transaction Details {" +
                "debtor=" + debtor.getValue() +
                ", creditor=" + creditor.getValue() +
                ", amount=" + amount +
                ", currency=" + currency +
                ", purpose='" + purpose + '\'' +
                ", timestamp=" + timestamp +
                " }";
    }


    public Alias getCreditor() {
        return creditor;
    }

    public Alias getDebtor() {
        return debtor;
    }

    public String getCurrency() {
        return currency;
    }
}
