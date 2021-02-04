package co.arnaldi.operation;

import org.springframework.stereotype.Component;

@Component("EXITOperation")
public class ExitOperation extends CommandOperation {

    @Override
    boolean execute() throws IllegalArgumentException {
        return false;
    }

    @Override
    void validateInput() throws IllegalArgumentException {

        if(this.getArgs().length > 1){
            throw new IllegalArgumentException("This command does not need any input parameter");
        }
    }
}
