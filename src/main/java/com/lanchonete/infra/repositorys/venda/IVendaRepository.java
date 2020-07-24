package com.lanchonete.infra.repositorys.venda;

import java.util.List;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.domain.entities.venda.Venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendaRepository extends JpaRepository<Venda, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM venda WHERE tipo_venda like ?1")
    List<Venda> findByTipoVenda(String tipoVenda);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM venda as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM venda as c where c.ativo = true")
	Page<VendaListDto> findAllDto(PageRequest of);
}
