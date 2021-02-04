package co.arnaldi.service;

import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayServiceImpl implements PayService {

    private AccountService accountService;

    private DebtService debtService;

    private TransactionService transactionService;

    public PayServiceImpl(AccountService accountService, DebtService debtService, TransactionService transactionService) {
        this.accountService = accountService;
        this.debtService = debtService;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Account pay(Account account, String accDestinationOwner, BigDecimal payAmt) throws IllegalArgumentException {
        //check balance
        //
        // if sufficient update balance and make transactions
        // else use all balance , make transactions. for the remaining add to debt.
        // else no balance , add to debt

        BigDecimal balance = account.getBalance();

        if(!accountService.isAccountExistByName(accDestinationOwner)){
            throw new IllegalArgumentException("Destination Account does not exist");
        }

        if(accDestinationOwner.equals(account.getName())){
            throw new IllegalArgumentException("Can not pay to same account");
        }

        Account destinationAccount = accountService.getAccountByName(accDestinationOwner);

        //check for debitor
        Optional<Debt> debitorOpt = debtService.getDebtBaseOnCreditorId(destinationAccount, account.getId());
        if(debitorOpt.isPresent()){
            Debt debitor = debitorOpt.get();
            int debitorCompare = debitor.getAmount().compareTo(payAmt);
            //if debt < pay amt set debt to 0 and add remaing pay amt to dest acc balance
            //if debt = pay set debt to 0
            //if debt > pay reduce debt by pay amt
            if(debitorCompare >= 0){
                BigDecimal newDebtAmt = debitor.getAmount().subtract(payAmt);
                debitor.setAmount(newDebtAmt);
                debtService.save(debitor);
                payAmt = BigDecimal.ZERO;
            }else{
                payAmt = payAmt.subtract(debitor.getAmount());
                debitor.setAmount(BigDecimal.ZERO);
                debtService.save(debitor);
            }

        }

        if(payAmt.compareTo(BigDecimal.ZERO) > 0){//check if still got amount to pay
            int compareBalanceWithPayment = balance.compareTo(payAmt);
            if(compareBalanceWithPayment >= 0){//check if got enough balance to pay

                BigDecimal newBalance = balance.subtract(payAmt);
                account.setBalance(newBalance);
                transactionService.doTransaction(account,destinationAccount.getId(), payAmt, false);
            }else{
                BigDecimal debtAmt = payAmt.subtract(balance);

                Optional<Debt> debtOpt = debtService.getDebtBaseOnCreditorId(account, destinationAccount.getId());

                Debt debt;
                //check for own debt
                if(debtOpt.isPresent()){
                    debt = debtOpt.get();
                    BigDecimal tem = debt.getAmount();
                    BigDecimal debtTempAmt = debt.getAmount().add(debtAmt);
                    debt.setAmount(debtTempAmt);
                    debtService.save(debt);

                }else{
                    debt = new Debt();
                    debt.setDebitorAccount(account);
                    debt.setCreditorAccountId(destinationAccount.getId());
                    debt.setAmount(debtAmt);
                    debtService.save(debt);
                    account.getDebts().add(debt);
                }

                transactionService.doTransaction(account,destinationAccount.getId(), payAmt, false);

                account.setBalance(BigDecimal.ZERO);

            }
        }

        account = accountService.saveSuppliedAcc(account);

        return account;
    }

}
