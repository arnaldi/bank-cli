package co.arnaldi.repository;

import co.arnaldi.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{

     boolean existsByName(String name);

     @EntityGraph(value = "graph.account.transactions")
     Account findByName(String name);
}
