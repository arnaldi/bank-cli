package co.arnaldi.operation;

import co.arnaldi.dto.ActiveAccount;

public abstract class CommandOperation {

    String[] args;

    abstract boolean execute() throws IllegalArgumentException;

    abstract void validateInput() throws IllegalArgumentException;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

}
