package com.lanchonete.infra.repositorys.bebida;

import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface IBebidaRepository extends IBaseRepository<Bebida> {

}
