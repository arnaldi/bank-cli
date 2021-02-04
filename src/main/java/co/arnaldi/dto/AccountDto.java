package co.arnaldi.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountDto {

    private String name;
    private BigDecimal balance;

    private List<DebtDto> debts;
    private List<DebtDto> credits;

    public AccountDto(String name,BigDecimal balance, List<DebtDto> debts, List<DebtDto> credits) {
        this.name = name;
        this.balance = balance;
        this.debts = debts;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public List<DebtDto> getDebts() {
        return debts;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<DebtDto> getCredits() {
        return credits;
    }
}
