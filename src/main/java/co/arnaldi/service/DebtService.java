package co.arnaldi.service;

import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;

import java.util.List;
import java.util.Optional;

public interface DebtService {

    public boolean checkIfGotDebt(Account account);

    public List<Debt> getCreditors(Account account);

    public List<Debt> getDebitors(Long account);

    public Optional<Debt> getDebtBaseOnCreditorId(Account debitorAccount, Long creditorAccountId);

    public Debt save(Debt debt);

}
