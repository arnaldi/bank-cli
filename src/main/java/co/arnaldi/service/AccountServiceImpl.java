package co.arnaldi.service;

import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.DebtDto;
import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;
import co.arnaldi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private DebtService debtService;

    public AccountServiceImpl(AccountRepository accountRepository, DebtService debtService){

        this.accountRepository = accountRepository;
        this.debtService = debtService;
    }


    @Transactional
    @Override
    public Account getAccountLogin(String name) {

        Account account;
        if(accountRepository.existsByName(name)){
            account = accountRepository.findByName(name);
        }else{
            Account newAccount = new Account();
            newAccount.setName(name);
            account = accountRepository.save(newAccount);
        }

        return account;
    }

    @Transactional
    @Override
    public AccountDto getAccountDto(Account account){

        List<DebtDto> debts = new ArrayList<>();
        List<Debt> debtsList = debtService.getCreditors(account);
        for(Debt debt: debtsList){
            Account creditorAcc = getAccountById(debt.getCreditorAccountId()).get();
            DebtDto debtDto = new DebtDto(account.getName(), creditorAcc.getName(), debt.getAmount());
            debts.add(debtDto);
        }
        List<DebtDto> credits = new ArrayList<>();
        List<Debt> creditList = debtService.getDebitors(account.getId());
        for(Debt debt: creditList){
            Account creditorAcc = account;
            DebtDto debtDto = new DebtDto(debt.getDebitorAccount().getName(), creditorAcc.getName(), debt.getAmount());
            credits.add(debtDto);
        }
        AccountDto accountDto = new AccountDto(account.getName(), account.getBalance(), debts, credits);

        return accountDto;
    }

    @Transactional
    @Override
    public boolean isAccountExistByName(String name){
        return accountRepository.existsByName(name);
    }

    @Transactional
    @Override
    public Account getAccountByName(String name) {
        return accountRepository.findByName(name);
    }

    @Transactional
    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    @Override
    public Account saveSuppliedAcc(Account acc){
        return accountRepository.save(acc);
    }

}
