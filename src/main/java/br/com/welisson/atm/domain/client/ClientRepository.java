package br.com.welisson.atm.domain.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("select c from Client as c join c.login as l where l.account = :account and l.password = :password")
    Client getClientByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    @Query("select c from Client as c join c.login as l where l.account = :account and c.id != :id")
    Client findClientByAccount(@Param("account") String account, @Param("id") Long id);

    @Query("select c from Client as c join c.login as l where l.account = :account")
    Client findClientByAccount(@Param("account") String account);
}
