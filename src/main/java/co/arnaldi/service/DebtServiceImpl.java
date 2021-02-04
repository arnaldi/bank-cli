package co.arnaldi.service;

import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;
import co.arnaldi.repository.DebtRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DebtServiceImpl implements DebtService {

    private DebtRepository debtRepository;

    public DebtServiceImpl(DebtRepository debtRepository){
        this.debtRepository = debtRepository;
    }

    @Override
    @Transactional
    public boolean checkIfGotDebt(Account account) {
        boolean result = false;
        if (debtRepository.getListOfCreditor(account).size() > 0 ){
            result = true;
        }
        return result;
    }

    @Override
    @Transactional
    public List<Debt> getCreditors(Account account) {
        return debtRepository.getListOfCreditor(account);
    }

    @Override
    @Transactional
    public List<Debt> getDebitors(Long accountId) {
        return debtRepository.getListOfDebitor(accountId);
    }

    @Override
    @Transactional
    public Optional<Debt> getDebtBaseOnCreditorId(Account debitorAccount, Long creditorAccountId) {
        return debtRepository.getDebtBaseOnDebitorAndCreditor(debitorAccount, creditorAccountId);
    }

    @Override
    @Transactional
    public Debt save(Debt debt){
        return debtRepository.save(debt);
    }


}
