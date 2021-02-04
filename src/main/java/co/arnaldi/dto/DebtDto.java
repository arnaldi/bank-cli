package co.arnaldi.dto;

import java.math.BigDecimal;

public class DebtDto {

    private String debitorAccountName;
    private String creditorAccountName;
    private BigDecimal amount;

    public DebtDto(String debitorAccountName, String creditorAccountName, BigDecimal amount) {
        this.debitorAccountName = debitorAccountName;
        this.creditorAccountName = creditorAccountName;
        this.amount = amount;
    }

    public String getDebitorAccountName() {
        return debitorAccountName;
    }

    public String getCreditorAccountName() {
        return creditorAccountName;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
