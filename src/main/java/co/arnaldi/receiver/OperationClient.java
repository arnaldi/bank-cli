package co.arnaldi.receiver;

import co.arnaldi.operation.CommandOperation;
import co.arnaldi.operation.CommandOperationExecutor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OperationClient {

    private final BeanFactory beanFactory;
    private static final String OPERATION_NAME_SUFFIX= "Operation";

    private CommandOperationExecutor commandOperationExecutor;

    public OperationClient(BeanFactory beanFactory, CommandOperationExecutor commandOperationExecutor) {
        this.beanFactory = beanFactory;
        this.commandOperationExecutor = commandOperationExecutor;
    }

    public boolean execute(String[] args) throws UnsupportedOperationException{
        boolean result = false;
        if(args == null || args.length == 0){
            throw new UnsupportedOperationException("no command");
        }

        String commandArg = args[0];
        if(!Command.contains(commandArg)){
            throw new UnsupportedOperationException("Command not exist");
        }

        Command commandEnum = Command.of(commandArg);
        CommandOperation operation = beanFactory.getBean(getOperationBeanName(commandEnum.name()), CommandOperation.class);
        operation.setArgs(args);
        return commandOperationExecutor.executeOperation(operation);

    }

    private String getOperationBeanName(String name){
        return name + OPERATION_NAME_SUFFIX;
    }
}
