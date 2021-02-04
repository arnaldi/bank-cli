package co.arnaldi.operation;

import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import co.arnaldi.service.TopUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component("TOPUPOperation")
public class TopUpOperation extends CommandOperation{

    private ActiveAccount activeAccount;

    private AccountService accountService;

    private TopUpService topUpService;

    private Logger logger = LoggerFactory.getLogger(TopUpOperation.class);

    public TopUpOperation(TopUpService topUpService, AccountService accountService, ActiveAccount activeAccount){
        this.topUpService = topUpService;
        this.accountService = accountService;
        this.activeAccount = activeAccount;
    }

    @Override
    @Transactional
    boolean execute() throws IllegalArgumentException {
        validateInput();
        Account account = topUpService.topup(activeAccount.getAccount(), new BigDecimal(this.getArgs()[1]));
        activeAccount.setAccount(account);
        AccountDto accountDto = accountService.getAccountDto(account);
        logger.info(" Your balance is : "+accountDto.getBalance());
        accountDto.getDebts().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0).forEach(debt -> logger.info("Owing "+debt.getAmount() +"to "+ debt.getCreditorAccountName()));
        accountDto.getCredits().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0 ).forEach(debt -> logger.info("Owing "+debt.getAmount() +"from "+ debt.getDebitorAccountName()));
        return true;
    }

    @Override
     void validateInput() throws IllegalArgumentException{
        if(this.getArgs().length < 2){
            throw new IllegalArgumentException("Please input your balance for topup");
        }

        try {
            BigDecimal topUpAmt = new BigDecimal(this.getArgs()[1]);
            if(topUpAmt.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Not Valid Amount for Top Up");
        }catch (NumberFormatException nfe){
            throw new IllegalArgumentException("Not Valid Amount for Top Up");
        }

        if(activeAccount.getAccount() == null){
            throw new UnsupportedOperationException("Please login first before call this command");
        }

    }
}
