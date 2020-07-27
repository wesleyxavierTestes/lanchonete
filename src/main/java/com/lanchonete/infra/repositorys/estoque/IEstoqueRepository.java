package com.lanchonete.infra.repositorys.estoque;

import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueRepository extends JpaRepository<AbstractEstoque, Long> {

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'S'", 
        countQuery = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'S'")
	Page<EstoqueSaida> listLeave(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM estoque as c where cestoque_tipo = 'E'", 
        countQuery = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'E'")
	Page<EstoqueEntrada> listEntrance(PageRequest of);
}
