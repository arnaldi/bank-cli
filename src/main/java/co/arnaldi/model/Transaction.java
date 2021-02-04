package co.arnaldi.model;

import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "sourceaccountid")
    protected Account account;

    @Column
    protected Long destinationAccountId;

    @Column
    protected BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date",insertable = false)
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    protected Date transactionDate;

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
