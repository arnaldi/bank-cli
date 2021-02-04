package co.arnaldi.service;

import co.arnaldi.model.Account;

import java.math.BigDecimal;

public interface TopUpService {

    public Account topup(Account account, BigDecimal topUpAmt) throws IllegalArgumentException;
}
