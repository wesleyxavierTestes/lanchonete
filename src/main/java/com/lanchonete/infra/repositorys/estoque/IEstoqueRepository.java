package com.lanchonete.infra.repositorys.estoque;

import java.util.List;

import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.estoque.IEstoque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueRepository extends JpaRepository<AbstractEstoque, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM estoque WHERE tipo_estoque like ?1")
    List<IEstoque> findByTipoEstoque(String tipoEstoque);

    @Query(nativeQuery = true, value = "SELECT (c.*) FROM estoque as c", countQuery = "SELECT (c.nome, c.nome, c.nome) FROM estoque")
    List<IEstoque> findAll(PageRequest of);

    @Query(nativeQuery = true, value = "SELECT (c.*) FROM estoque as c", countQuery = "SELECT (c.nome, c.nome, c.nome) FROM estoque")
    List<EstoqueSaida> findAllSaida(PageRequest of);

    @Query(nativeQuery = true, 
    value = "SELECT (c.*) FROM estoque as c", 
    countQuery = "SELECT (c.nome, c.nome, c.nome) FROM estoque")
    List<EstoqueEntrada> findAllEntrada(PageRequest of);

    @Query(nativeQuery = true, value = "SELECT (c.*) FROM estoque as c", countQuery = "SELECT (c.nome, c.nome, c.nome) FROM estoque")
    Page<EstoqueListDto> findAllDto(PageRequest of);
}
