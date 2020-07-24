package com.lanchonete.infra.repositorys.combo;

import java.util.List;

import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.domain.entities.cardapio.combo.Combo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IComboRepository extends JpaRepository<Combo, Long>  {

    @Query(nativeQuery = true, value = "SELECT * FROM combo WHERE tipo_combo like ?1")
    List<Combo> findByTipoCombo(String tipoCombo);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM combo as c",
        countQuery = "SELECT (c.nome, c.nome, c.nome) FROM combo")
	Page<ComboListDto> findAllDto(PageRequest of);
}
