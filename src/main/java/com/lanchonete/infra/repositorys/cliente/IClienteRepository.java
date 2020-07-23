package com.lanchonete.infra.repositorys.cliente;

import java.util.List;
import java.util.Optional;

import com.lanchonete.domain.entities.cliente.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM cliente WHERE tipo_cliente like ?1")
    List<Cliente> findByTipoCliente(String tipoCliente);
}
