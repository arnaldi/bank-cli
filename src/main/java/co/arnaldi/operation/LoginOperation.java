package co.arnaldi.operation;

import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component("LOGINOperation")
public class LoginOperation extends CommandOperation {

    private ActiveAccount activeAccount;

    private AccountService accountService;

    private Logger logger = LoggerFactory.getLogger(LoginOperation.class);

    public LoginOperation(AccountService accountService, ActiveAccount activeAccount){
        this.accountService = accountService;
        this.activeAccount = activeAccount;
    }

    @Override
    @Transactional
    public boolean execute() throws IllegalArgumentException {
        validateInput();
        Account activeAcc = accountService.getAccountLogin(this.getArgs()[1]);
        activeAccount.setAccount(activeAcc);
        AccountDto account = accountService.getAccountDto(activeAcc);
        logger.info("Hello "+account.getName() + "!");
        logger.info("Your balance is " +account.getBalance());
        account.getDebts().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0).forEach(debt -> logger.info("Owing "+debt.getAmount() +" to "+ debt.getCreditorAccountName()));
        account.getCredits().stream().filter(d -> d.getAmount().compareTo(BigDecimal.ZERO) > 0 ).forEach(debt -> logger.info("Owing "+debt.getAmount() +" from "+ debt.getDebitorAccountName()));
        return true;
    }

    @Override
    void validateInput() throws IllegalArgumentException {
        if(this.getArgs().length < 2){
            throw new IllegalArgumentException("Please input your name for login");
        }
    }
}
