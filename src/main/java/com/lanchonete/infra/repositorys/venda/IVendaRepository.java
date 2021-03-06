package com.lanchonete.infra.repositorys.venda;

import java.util.List;

import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.infra.repositorys.IBaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendaRepository extends IBaseRepository<Venda>  {

    @Query(nativeQuery = true, value = "SELECT * FROM venda WHERE tipo_venda like ?1")
    List<Venda> findByTipoVenda(String tipoVenda);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM venda as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM venda as c where c.ativo = false")
	Page<Venda> listCancel(PageRequest of);
}
