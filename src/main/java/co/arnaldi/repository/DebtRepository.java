package co.arnaldi.repository;

import co.arnaldi.model.Account;
import co.arnaldi.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>{

    @Query(value = "SELECT * FROM Debt d " +
            " WHERE d.creditorAccountId = ?1 and amount > 0; ", nativeQuery = true)
    List<Debt> getListOfDebitor(Long creditorAccountId);

    @Query(value = "SELECT * FROM Debt " +
            " WHERE debitoraccount = :account and amount > 0; ", nativeQuery = true)
    List<Debt> getListOfCreditor(@Param("account")Account account);


    @Query(value = "SELECT * FROM Debt " +
            " WHERE debitoraccount = :debitorAccount and creditorAccountId = :creditorAccountId ", nativeQuery = true)
    Optional<Debt> getDebtBaseOnDebitorAndCreditor(@Param("debitorAccount") Account debitorAccount, @Param("creditorAccountId") Long creditorAccountId);

}
