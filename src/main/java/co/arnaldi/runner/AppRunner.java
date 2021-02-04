package co.arnaldi.runner;

import co.arnaldi.receiver.OperationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {

    private OperationClient operationClient;

    public AppRunner(OperationClient operationClient){
        this.operationClient = operationClient;
    }

    Logger logger = LoggerFactory.getLogger(AppRunner.class);

    @Override
    public void run(String... args) throws Exception {

        boolean alive = true;

        while(alive){
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            try{
                alive = operationClient.execute(line.split(" "));
            }catch (UnsupportedOperationException uoe){
                logger.error(uoe.getMessage());
            }

        }

    }
}
