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

    @Query(nativeQuery = true, value = "SELECT * FROM pedido WHERE tipo_pedido like ?1")
    List<Pedido> findByTipoPedido(String tipoPedido);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM pedido as c where c.ativo = true")
	Page<PedidoListDto> findAllDto(PageRequest of);
}
