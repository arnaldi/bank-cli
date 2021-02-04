package co.arnaldi.operation;

import co.arnaldi.dto.ActiveAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("LOGOUTOperation")
public class LogoutOperation extends CommandOperation{

    private ActiveAccount activeAccount;

    Logger logger = LoggerFactory.getLogger(LogoutOperation.class);

    public LogoutOperation(ActiveAccount activeAccount){
        this.activeAccount = activeAccount;
    }

    @Override
    boolean execute() throws IllegalArgumentException {
        logger.info("Good bye "+ activeAccount.getAccount().getName());
        activeAccount.setAccount(null);
        return true;
    }

    @Override
    void validateInput() throws IllegalArgumentException {
        if(this.getArgs().length > 1){
            throw new IllegalArgumentException("This command does not need any input parameter");
        }
    }
}
