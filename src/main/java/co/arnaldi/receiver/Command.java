package co.arnaldi.receiver;

import java.util.HashMap;
import java.util.Map;

public enum Command {

    LOGIN("login") ,
    TOPUP("topup") ,
    PAY("pay") ,
    LOGOUT("logout") ,
    EXIT("exit");

    private final String value;

    Command(String value){
        this.value = value;
    }

    private static final Map<String, Command> map = new HashMap<>(values().length, 1);

    static {
        for(Command c : values()) map.put(c.value, c);
    }

    public static Command of(String value){
        Command result = map.get(value);
        if(result == null){
            throw new IllegalArgumentException("Invalid command: "+ value);
        }
        return result;
    }

    public static boolean contains(String command){
        boolean result = true;
        try{
           Command commandTemp =  of(command);
        }catch (IllegalArgumentException iae){
            result = false;
        }
        return result;
    }


    @Override
    public String toString() {
        return value;
    }
}
