package com.lanchonete.infra.repositorys.bebida;

import java.util.List;

import com.lanchonete.domain.entities.bebida.Bebida;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBebidaRepository extends JpaRepository<Bebida, Long>  {

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM bebida as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM bebida as c where c.ativo = true")
    Page<Bebida> listActive(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM bebida as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM bebida as c where c.ativo = false")
    Page<Bebida> listDesactive(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM bebida as c where c.ativo = true and c.id = ?1 limit 1")
    Bebida findByIdAtive(long id);
}
