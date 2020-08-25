package com.lanchonete.infra.repositorys.estoque;

import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueSaidaRepository extends IBaseRepository<EstoqueSaida>{
    
}