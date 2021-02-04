package co.arnaldi.model;

import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Debt {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "debitoraccount")
    protected Account debitorAccount;

    @Column(name = "creditoraccountid")
    protected Long creditorAccountId;

    @Column
    protected BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date",insertable = false, updatable = false)
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    protected Date transactionDate;

    public Debt() {
    }

    public Debt(Account debitorAccount, Long creditorAccountId, BigDecimal amount) {
        this.debitorAccount = debitorAccount;
        this.creditorAccountId = creditorAccountId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Account getDebitorAccount() {
        return debitorAccount;
    }

    public Long getCreditorAccountId() {
        return creditorAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setDebitorAccount(Account debitorAccount) {
        this.debitorAccount = debitorAccount;
    }

    public void setCreditorAccountId(Long creditorAccountId) {
        this.creditorAccountId = creditorAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
