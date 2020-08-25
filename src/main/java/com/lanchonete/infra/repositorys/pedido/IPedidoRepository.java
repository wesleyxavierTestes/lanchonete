package com.lanchonete.infra.repositorys.pedido;

import java.util.List;

import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends IBaseRepository<Pedido>  {

    @Query(nativeQuery = true, value = "SELECT * FROM pedido WHERE estado like ?1 and c.ativo = true")
    List<Pedido> findByEstado(String estado);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.estado = 'Cancelado' and c.ativo = false",
        countQuery = "SELECT (c.*) FROM pedido as c where c.estado = 'Cancelado' and c.ativo = false")
    Page<Pedido> listCancel(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM pedido as c where c.ativo = true")
    Page<Pedido> listActive(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.data_cadastro > ?1",
        countQuery = "SELECT (c.*) FROM pedido as c where c.data_cadastro > ?1")
    Page<Pedido> listDay(String data, PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM pedido as c where c.cliente_id = ?1",
        countQuery = "SELECT (c.*) FROM pedido as c where c.cliente_id = ?1")
    Page<Pedido> listClient(long id, PageRequest pageRequest);
}
