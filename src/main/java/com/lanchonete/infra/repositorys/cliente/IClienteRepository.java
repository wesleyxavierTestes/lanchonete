package com.lanchonete.infra.repositorys.cliente;

import java.util.List;

import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.domain.entities.cliente.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM cliente WHERE tipo_cliente like ?1")
    List<Cliente> findByTipoCliente(String tipoCliente);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cliente as c",
        countQuery = "SELECT (c.*) FROM cliente as c")
    Page<ClienteListDto> findAllDto(PageRequest of);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cliente as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM cliente as c where c.ativo = true")
    Page<ClienteListDto> listActiveDto(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cliente as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM cliente as c where c.ativo = false")
    Page<ClienteListDto> listDesactiveDto(PageRequest pageRequest);
    
}
