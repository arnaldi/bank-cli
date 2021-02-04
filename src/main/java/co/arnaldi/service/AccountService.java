package co.arnaldi.service;

import co.arnaldi.dto.AccountDto;
import co.arnaldi.model.Account;


import java.util.Optional;

public interface AccountService {

    public Account getAccountLogin(String name);

    public AccountDto getAccountDto(Account account);
    public Account saveSuppliedAcc(Account account);
    public boolean isAccountExistByName(String name);
    public Account getAccountByName(String name);
    public Optional<Account> getAccountById(Long id);
}
