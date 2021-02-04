package co.arnaldi.dto;

import co.arnaldi.model.Account;
import org.springframework.stereotype.Component;

@Component
public class ActiveAccount {

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
