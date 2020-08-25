package com.lanchonete.infra.repositorys.cliente;

import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends IBaseRepository<Endereco> {

}
