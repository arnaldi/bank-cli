package co.arnaldi;

import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.model.Account;
import co.arnaldi.repository.AccountRepository;
import co.arnaldi.service.AccountService;
import co.arnaldi.service.AccountServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"co.arnaldi"})
@EnableJpaRepositories
@EntityScan("co.arnaldi.*")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

    @Bean
    ActiveAccount accountActive(){
        return new ActiveAccount();
    }
}
