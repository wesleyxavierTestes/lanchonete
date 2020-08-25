package com.lanchonete.infra.repositorys.outros;

import com.lanchonete.domain.entities.outros.Outros;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOutrosRepository extends IBaseRepository<Outros>  {

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM outros as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM outros as c where c.ativo = true")
    Page<Outros> listActive(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM outros as c where c.ativo = true and c.id = ?1 limit 1")
    Outros findByIdAtive(long id);
}
