package com.lanchonete.infra.repositorys.estoque;

import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueRepository extends IBaseRepository<AbstractEstoque> {

}
