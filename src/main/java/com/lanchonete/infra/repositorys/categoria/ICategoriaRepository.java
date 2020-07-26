package com.lanchonete.infra.repositorys.categoria;


import com.lanchonete.domain.entities.categoria.Categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>  {

    @Query(nativeQuery = true, 
            value = "SELECT * FROM categoria WHERE nome like *?1* limit 1")
    Categoria findByName(String tipoCategoria);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM categoria as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM categoria as c where c.ativo = true")
    Page<Categoria> listActive(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM categoria as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM categoria as c where c.ativo = false")
    Page<Categoria> listDesactive(PageRequest pageRequest);
    
}