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

    @Query(
    nativeQuery = true, 
    value = "SELECT * FROM estoque WHERE tipo_estoque like ?1")
    List<IEstoque> findByTipoEstoque(String tipoEstoque);

    @Query(
    nativeQuery = true, 
    value = "SELECT (c.*) FROM estoque as c", 
    countQuery = "SELECT (c.*) FROM estoque as c")
    List<IEstoque> findAll(PageRequest of);

    @Query(
    nativeQuery = true, 
    value = "SELECT (c.*) FROM estoque as c where c.ativo = true", 
    countQuery = "SELECT (c.*) FROM estoque as c where c.ativo = true")
    List<EstoqueSaida> findAllSaida(PageRequest of);

    @Query(
    nativeQuery = true, 
    value = "SELECT (c.*) FROM estoque as c", 
    countQuery = "SELECT (c.*) FROM estoque as c")
    List<EstoqueEntrada> findAllEntrada(PageRequest of);

    @Query(
    nativeQuery = true, 
    value = "SELECT (c.*) FROM estoque as c where c.ativo = true", 
    countQuery = "SELECT (c.*) FROM estoque as c where c.ativo = true")
    Page<EstoqueListDto> findAllDto(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'S'", 
        countQuery = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'S'")
	Page<EstoqueListDto> listLeave(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM estoque as c where cestoque_tipo = 'E'", 
        countQuery = "SELECT (c.*) FROM estoque as c where c.estoque_tipo = 'E'")
	Page<EstoqueListDto> listEntrance(PageRequest of);
}
