package com.lanchonete.infra.repositorys.lanche;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILancheRepository extends JpaRepository<Lanche, Long>  {
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM lanche as c where c.ativo = true")
    Page<Lanche> listActive(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM lanche as c where c.ativo = false")
	Page<LancheListDto> listDesactive(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c where c.ativo = true and c.id = ?1 limit 1")
        Lanche findByIdAtive(long id);
}
