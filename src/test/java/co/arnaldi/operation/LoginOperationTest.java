package co.arnaldi.operation;

import co.arnaldi.BaseTestMockCase;
import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.dto.DebtDto;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginOperationTest extends BaseTestMockCase{

    @Mock
    private AccountService accountService;

    @Mock
    ActiveAccount activeAccount;

    @InjectMocks
    private CommandOperation loginOperation = new LoginOperation(accountService,activeAccount);

    @BeforeEach
    void setMockOutput(){

        AccountDto acc = new AccountDto("Bob",new BigDecimal(100), new ArrayList<DebtDto>(), new ArrayList<DebtDto>());
        Account account = new Account();
        account.setName("Bob");
        account.setBalance(new BigDecimal(100));
        when(accountService.getAccountLogin("Bob")).thenReturn(account);
        when(accountService.getAccountDto(account)).thenReturn(acc);
    }

    @Test
    void executeWithNoUserNameShouldThrowIllegalArgumentException() {
        String[] args = {"login"};
        loginOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> loginOperation.execute());
        assertEquals("Please input your name for login", throwException.getMessage());
    }

    @Test
    void executeWithValidArgsShouldReturnTrue(){
        String[] args = {"login", "Bob"};
        loginOperation.setArgs(args);
        assertTrue(loginOperation.execute());
    }
}