package com.csgp.arda.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByAccessToken(String token);

    @Query("""
    select t from Token t inner join User u on t.user.id = u.id
     where t.user.id = :userId and t.isLoggedOut = false""")
    List<Token> findAllAccessTokensByUser(@Param("userId") Long userId); // el @Param fue necesario para que funcionen los test que usan esta consulta

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId); // el @Param fue necesario para que funcionen los test que usan esta consulta

}
