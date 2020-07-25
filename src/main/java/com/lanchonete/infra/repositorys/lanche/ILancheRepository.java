package com.lanchonete.infra.repositorys.lanche;

import java.util.List;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILancheRepository extends JpaRepository<Lanche, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM lanche WHERE tipo_lanche like ?1")
    List<Lanche> findByTipoLanche(String tipoLanche);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c",
        countQuery = "SELECT (c.*) FROM lanche as c")
    Page<LancheListDto> findAllDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM lanche as c where c.ativo = true")
    Page<LancheListDto> listActiveDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM lanche as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM lanche as c where c.ativo = false")
	Page<LancheListDto> listDesactiveDto(PageRequest of);
}
