package co.arnaldi.operation;

import co.arnaldi.BaseTestMockCase;
import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.dto.DebtDto;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import co.arnaldi.service.TopUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TopUpOperationTest extends BaseTestMockCase{

    @Mock
    TopUpService topUpService;

    @Mock
    private ActiveAccount activeAccount;

    @Mock
    private AccountService accountService;

    @InjectMocks
    CommandOperation topUpOperation = new TopUpOperation(topUpService, accountService, activeAccount);



    @Test
    void executeWithNoAmountShouldThrowIllegalArgumentException() {
        String[] args = {"topup"};
        topUpOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> topUpOperation.execute());
        assertEquals("Please input your balance for topup", throwException.getMessage());
    }

    @Test
    void executeWithNegativeAmountNumberShouldThrowIllegalArgumentException() {
        String[] args = {"topup", "-10"};
        topUpOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> topUpOperation.execute());
        assertEquals("Not Valid Amount for Top Up", throwException.getMessage());
    }

    @Test
    void executeWithNotValidAmountNumberShouldThrowIllegalArgumentException() {
        String[] args = {"topup", "more"};
        topUpOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> topUpOperation.execute());
        assertEquals("Not Valid Amount for Top Up", throwException.getMessage());
    }

    @Test
    void executeWithValidArgsShouldReturnTrue(){
        Account currentAcc = new Account();
        currentAcc.setName("Bob");
        currentAcc.setBalance(BigDecimal.ZERO);

        Account afterTopUpAcc = new Account();
        afterTopUpAcc.setName("Bob");
        afterTopUpAcc.setBalance(BigDecimal.TEN);

        AccountDto acc = new AccountDto("Bob",BigDecimal.TEN, new ArrayList<DebtDto>(), new ArrayList<DebtDto>());

        when(activeAccount.getAccount()).thenReturn(currentAcc);
        when(topUpService.topup(currentAcc,BigDecimal.TEN)).thenReturn(afterTopUpAcc);
        when(accountService.getAccountDto(afterTopUpAcc)).thenReturn(acc);

        String[] args = {"topup", "10"};
        topUpOperation.setArgs(args);
        assertTrue(topUpOperation.execute());
    }

    @Test
    void executeWithValidArgsWithoutloginShouldThrowIllegalArgumentException(){
        when(activeAccount.getAccount()).thenReturn(null);

        String[] args = {"topup", "10"};
        topUpOperation.setArgs(args);
        UnsupportedOperationException throwException = assertThrows(UnsupportedOperationException.class, () -> topUpOperation.execute());
        assertEquals("Please login first before call this command", throwException.getMessage());
    }
}