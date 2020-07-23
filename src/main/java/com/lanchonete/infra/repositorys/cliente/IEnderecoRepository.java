package com.lanchonete.infra.repositorys.cliente;


import com.lanchonete.domain.entities.cliente.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Long>  {

}
