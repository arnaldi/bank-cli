package co.arnaldi.operation;

import co.arnaldi.BaseTestMockCase;
import co.arnaldi.dto.AccountDto;
import co.arnaldi.dto.ActiveAccount;
import co.arnaldi.dto.DebtDto;
import co.arnaldi.model.Account;
import co.arnaldi.service.AccountService;
import co.arnaldi.service.PayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class PayOperationTest extends BaseTestMockCase{

    @Mock
    private ActiveAccount activeAccount;

    @Mock
    private AccountService accountService;

    @Mock
    private PayService payService;

    @InjectMocks
    CommandOperation payOperation = new PayOperation(payService,accountService,activeAccount);

    @Test
    void executeWithNoAmountShouldThrowIllegalArgumentException() {
        String[] args = {"pay"};
        payOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> payOperation.execute());
        assertEquals("Please input using the following format : pay <destination> <amount>", throwException.getMessage());
    }

    @Test
    void executeWithNegativeAmountNumberShouldThrowIllegalArgumentException() {
        String[] args = {"pay","Mike", "-10"};
        payOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> payOperation.execute());
        assertEquals("Not Valid Amount for Pay", throwException.getMessage());
    }

    @Test
    void executeWithNotValidAmountNumberShouldThrowIllegalArgumentException() {
        String[] args = {"pay","Mike", "more"};
        payOperation.setArgs(args);
        IllegalArgumentException throwException = assertThrows(IllegalArgumentException.class, () -> payOperation.execute());
        assertEquals("Not Valid Amount for Pay", throwException.getMessage());
    }

    @Test
    void executeWithValidArgsShouldReturnTrue(){
        Account currentAcc = new Account();
        currentAcc.setName("Bob");
        currentAcc.setBalance(BigDecimal.TEN);

        Account afterPayAcc = new Account();
        afterPayAcc.setName("Bob");
        afterPayAcc.setBalance(BigDecimal.ZERO);

        AccountDto acc = new AccountDto("Bob",new BigDecimal(100), new ArrayList<DebtDto>(), new ArrayList<DebtDto>());

        when(activeAccount.getAccount()).thenReturn(currentAcc);
        when(payService.pay(currentAcc, "Mike", BigDecimal.TEN)).thenReturn(afterPayAcc);
        when(accountService.getAccountByName(activeAccount.getAccount().getName())).thenReturn(afterPayAcc);
        when(accountService.getAccountDto(afterPayAcc)).thenReturn(acc);

        String[] args ={"pay","Mike", "10"};
        payOperation.setArgs(args);
        assertTrue(payOperation.execute());
    }

    @Test
    void executeWithValidArgsWithoutloginShouldThrowIllegalArgumentException(){
        when(activeAccount.getAccount()).thenReturn(null);

        String[] args ={"pay","Mike", "10"};
        payOperation.setArgs(args);
        UnsupportedOperationException throwException = assertThrows(UnsupportedOperationException.class, () -> payOperation.execute());
        assertEquals("Please login first before call this command", throwException.getMessage());
    }
}