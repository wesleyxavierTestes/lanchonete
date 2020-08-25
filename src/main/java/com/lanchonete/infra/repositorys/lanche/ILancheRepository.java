package com.lanchonete.infra.repositorys.lanche;

import java.util.Collection;
import java.util.UUID;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILancheRepository extends IBaseRepository<Lanche>  {
    Collection<Lanche> findAllByNomeContaining(String nome);
    Collection<Lanche> findAllByCodigo(UUID codigo);
    Lanche findByCodigo(UUID codigo);

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


    Lanche findByIdEqualsAndAtivoTrue(long id);
}
