package com.lanchonete.infra.repositorys.estoque;

import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueEntradaRepository extends IBaseRepository<EstoqueEntrada>{
    
}