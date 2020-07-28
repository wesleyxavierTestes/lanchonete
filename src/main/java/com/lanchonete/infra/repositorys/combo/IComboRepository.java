package com.lanchonete.infra.repositorys.combo;

import com.lanchonete.domain.entities.cardapio.combo.Combo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IComboRepository extends JpaRepository<Combo, Long>  {
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM combo as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM combo as c where c.ativo = true")
    Page<Combo> listActive(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM combo as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM combo as c where c.ativo = false")
    Page<Combo> listDesactive(PageRequest pageRequest);
    
}
