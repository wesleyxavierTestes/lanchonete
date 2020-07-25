package com.lanchonete.infra.repositorys.pedido;

import java.util.List;

import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.domain.entities.pedido.Pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM pedido WHERE estado like ?1")
    List<Pedido> findByEstado(String estado);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c",
        countQuery = "SELECT (c.*) FROM pedido as c")
    Page<PedidoListDto> findAllDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM pedido as c where c.ativo = false")
	Page<PedidoListDto> listCancelDto(PageRequest of);
}
