package com.lanchonete.infra.repositorys.produto;

import com.lanchonete.domain.entities.produto.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long>, IBaseProdutoRepository {

   
}
