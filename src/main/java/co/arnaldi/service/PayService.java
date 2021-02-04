package co.arnaldi.service;

import co.arnaldi.model.Account;

import java.math.BigDecimal;

public interface PayService {

    public Account pay(Account account, String accDestinationOwner, BigDecimal payAmt) throws IllegalArgumentException;
}
