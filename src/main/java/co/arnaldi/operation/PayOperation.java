package co.arnaldi.operation;

import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import co.arnaldi.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component("PAYOperation")
public class PayOperation extends CommandOperation{

    private ActiveAccount activeAccount;

    private AccountService accountService;

    private PayService payService;

    private Logger logger = LoggerFactory.getLogger(PayOperation.class);

    private final int divider = 5;

    public  PayOperation(PayService payService, AccountService accountService, ActiveAccount activeAccount){
        this.payService = payService;
        this.accountService = accountService;
        this.activeAccount = activeAccount;
    }

    @Override
    @Transactional
    boolean execute() throws IllegalArgumentException {
        validateInput();
        BigDecimal payAmount = new BigDecimal(this.getArgs()[2]).setScale(2,BigDecimal.ROUND_FLOOR);
        //simulate pay in parts
        List<BigDecimal> amountList = divideAmt(payAmount);
        amountList.stream().forEach(amount -> payService.pay(activeAccount.getAccount(), this.getArgs()[1], amount));
        Account account = accountService.getAccountByName(activeAccount.getAccount().getName());
        activeAccount.setAccount(account);
        AccountDto accountDto = accountService.getAccountDto(account);
        logger.info(" Your balance is : "+account.getBalance());
        accountDto.getDebts().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0).forEach(debt -> logger.info(" Owing "+debt.getAmount() +" to "+ debt.getCreditorAccountName()));
        accountDto.getCredits().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0 ).forEach(debt -> logger.info(" Owing "+debt.getAmount() +" from "+ debt.getDebitorAccountName()));
        return true;
    }

    @Override
    void validateInput() throws IllegalArgumentException {
        if(this.getArgs().length < 3){
            throw new IllegalArgumentException("Please input using the following format : pay <destination> <amount>");
        }

        try {
            BigDecimal payAmt = new BigDecimal(this.getArgs()[2]);
            if(payAmt.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Not Valid Amount for Pay");
        }catch (NumberFormatException nfe){
            throw new IllegalArgumentException("Not Valid Amount for Pay");
        }

        if(activeAccount.getAccount() == null){
            throw new UnsupportedOperationException("Please login first before call this command");
        }

    }

    private List<BigDecimal> divideAmt(BigDecimal amount){
        List<BigDecimal> returnVal = new ArrayList<>();
        BigDecimal dividerBigDecimal = BigDecimal.valueOf(divider);
        BigDecimal divideResult = amount.divide(dividerBigDecimal,BigDecimal.ROUND_FLOOR);
        BigDecimal tempValue = BigDecimal.ZERO;
        for(int i = 0 ; i < divider; i++){
            returnVal.add(divideResult);
            tempValue = tempValue.add(divideResult);
        }
        tempValue = tempValue.setScale(2,BigDecimal.ROUND_FLOOR);
        if(tempValue.compareTo(amount) < 0){
            BigDecimal amt = amount.subtract(tempValue);
            returnVal.add(amt);
        }
        return returnVal;
    }

}
