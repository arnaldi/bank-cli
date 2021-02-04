package co.arnaldi.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandOperationExecutor {

    Logger logger = LoggerFactory.getLogger(CommandOperationExecutor.class);

    public boolean executeOperation(CommandOperation commandOperation){
        boolean result = true;
        try{
            result = commandOperation.execute();
        }catch (IllegalArgumentException iae){
            logger.error(iae.getMessage());
        }
        return result;
    }
}
