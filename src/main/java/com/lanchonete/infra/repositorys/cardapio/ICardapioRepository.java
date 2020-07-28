package com.lanchonete.infra.repositorys.cardapio;

import com.lanchonete.domain.entities.cardapio.Cardapio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardapioRepository extends JpaRepository<Cardapio, Long>  {
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cardapio as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM cardapio as c where c.ativo = true")
    Page<Cardapio> listActive(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cardapio as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM cardapio as c where c.ativo = false")
    Page<Cardapio> listDesactive(PageRequest pageRequest);
    
}