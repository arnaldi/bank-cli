package co.arnaldi.service;

import co.arnaldi.model.Account;
import co.arnaldi.model.Transaction;

import java.math.BigDecimal;

public interface TransactionService {

    public Transaction save(Transaction transaction);

    public void doTopUp(Account account, BigDecimal topUpAmt);

    public void doTransaction(Account account, Long destinationId, BigDecimal amount, boolean isMyself);

}
