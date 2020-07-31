package com.lanchonete.infra.repositorys.produto;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbstractProdutoRepository extends  JpaRepository<AbstractProduto, Long>, IBaseProdutoRepository  {
    
}
