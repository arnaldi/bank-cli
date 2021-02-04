package co.arnaldi.service;

import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.model.Transaction;
import co.arnaldi.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    private AccountService accountService;

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService){
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void doTopUp(Account account, BigDecimal topUpAmt){
        BigDecimal balance = account.getBalance();
        balance = balance.add(topUpAmt);
        account.setBalance(balance);
        accountService.saveSuppliedAcc(account);

        doTransaction(account, account.getId(), topUpAmt, true);

    }

    @Override
    @Transactional
    public void doTransaction(Account account, Long destinationId, BigDecimal amount, boolean isMyself){
        Transaction transaction = new Transaction();
        transaction.setDestinationAccountId(destinationId);
        transaction.setAccount(account);
        transaction.setAmount(amount);

        save(transaction);

        account.getTransactions().add(transaction);
        accountService.saveSuppliedAcc(account);
        if(!isMyself){
            Optional<Account> destAcc = accountService.getAccountById(destinationId);
            Account destAccount = destAcc.get();
            destAccount.setBalance(destAccount.getBalance().add(amount));
            accountService.saveSuppliedAcc(destAccount);
            String destinationName = destAcc.get().getName();

            logger.info("Transferred " + amount + " to "+ destinationName);
        }

    }

}
