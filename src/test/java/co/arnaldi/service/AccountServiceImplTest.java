package co.arnaldi.service;

import co.arnaldi.BaseTestMockCase;
import co.arnaldi.model.Account;
import co.arnaldi.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest extends BaseTestMockCase {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DebtService debtService;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl(accountRepository,debtService);

    @Test
    void whenLoginWithNewUserShouldReturnUser() {

        Account acc = new Account("test");

        when(accountRepository.existsByName("test")).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(acc);

        Account newAcc = accountService.getAccountLogin("test");

        assertEquals(newAcc.getName(),acc.getName());
    }

    @Test
    void whenLoginWithExistingUserShouldReturnUser() {

        Account acc = new Account("test");

        when(accountRepository.existsByName("test")).thenReturn(true);
        when(accountRepository.findByName("test")).thenReturn(acc);

        Account existingAcc = accountService.getAccountLogin("test");

        assertEquals(existingAcc.getName(),acc.getName());
    }

    @Test
    void getAccountDto() {
    }


}