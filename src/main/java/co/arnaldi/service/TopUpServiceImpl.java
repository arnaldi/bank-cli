package co.arnaldi.service;

import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopUpServiceImpl implements TopUpService {

    private ActiveAccount activeAccount;

    private DebtService debtService;

    private AccountService accountService;

    private TransactionService transactionService;

    public TopUpServiceImpl(ActiveAccount activeAccount, DebtService debtService, AccountService accountService, TransactionService transactionService) {
        this.activeAccount = activeAccount;
        this.debtService = debtService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Account topup(Account account, BigDecimal topUpAmt) throws IllegalArgumentException{

        BigDecimal balance = account.getBalance();

        //check if got debt or not
        /*
            if got debt, use topup amt to reduce debt first. make transactions. if still got remaining topUpAmt after pay debt, then top up balance.
            if no debt, straight to top up
         */

        List<Debt> remainingDebts = account.getDebts().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());

        for (Debt debt: remainingDebts) {

            BigDecimal debtAmt;
            // reduce top up amt to debt amt
            // update debt
            //make topup
            debtAmt = BigDecimal.ZERO;
            int compare = topUpAmt.compareTo(debt.getAmount());
            if (compare >= 0){
                topUpAmt = topUpAmt.subtract(debt.getAmount());
                debt.setAmount(BigDecimal.ZERO);
                debtService.save(debt);
                transactionService.doTransaction(account,debt.getCreditorAccountId(),debt.getAmount(),false);
            }else{
                debtAmt = debt.getAmount().subtract(topUpAmt);
                topUpAmt = BigDecimal.ZERO;
                debt.setAmount(debtAmt);
                debtService.save(debt);
                break;
            }
        }

        if(topUpAmt.compareTo(BigDecimal.ZERO) > 0) transactionService.doTopUp(account,topUpAmt);

        accountService.saveSuppliedAcc(account);



        return account;

    }

}
