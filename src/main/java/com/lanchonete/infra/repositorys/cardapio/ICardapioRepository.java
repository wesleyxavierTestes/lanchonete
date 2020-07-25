package com.lanchonete.infra.repositorys.cardapio;

import java.util.List;

import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.domain.entities.cardapio.Cardapio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardapioRepository extends JpaRepository<Cardapio, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM cardapio WHERE tipo_cardapio like ?1")
    List<Cardapio> findByTipoCardapio(String tipoCardapio);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cardapio as c",
        countQuery = "SELECT (c.*) FROM cardapio as c")
    Page<CardapioListDto> findAllDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cardapio as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM cardapio as c where c.ativo = true")
    Page<CardapioListDto> listActiveDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cardapio as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM cardapio as c where c.ativo = false")
    Page<CardapioListDto> listDesactiveDto(PageRequest of);
    
}