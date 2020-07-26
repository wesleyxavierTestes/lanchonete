package com.lanchonete.infra.repositorys.produto;

import com.lanchonete.domain.entities.produto.entities.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long>  {

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM produto as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM produto as c where c.ativo = true")
    Page<Produto> listActive(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM produto as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM produto as c where c.ativo = false")
	Page<Produto> listDesactive(PageRequest of);
}
