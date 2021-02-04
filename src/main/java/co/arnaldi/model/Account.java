package co.arnaldi.model;

import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "graph.account.transactions", attributeNodes = {@NamedAttributeNode("transactions"), @NamedAttributeNode("debts")})
public class Account {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    protected Long id;

    @NotBlank(message = "Name may not be blank")
    @Column
    protected  String name;

    @Column(insertable = false)
    @org.hibernate.annotations.ColumnDefault("0.00")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    protected BigDecimal balance;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time",insertable = false)
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    protected Date createdTime;

    @OneToMany(mappedBy = "account")
    protected Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "debitorAccount")
    protected Set<Debt> debts = new HashSet<>();

    public Account(){

    }

    public Account(@NotBlank(message = "Name may not be blank") String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Debt> getDebts() {
        return debts;
    }

    public void setDebts(Set<Debt> debts) {
        this.debts = debts;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
