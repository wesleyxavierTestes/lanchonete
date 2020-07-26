package com.lanchonete.apllication.dto.combo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.lanchonete.domain.entities.produto.baseentity.IProdutoCombo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboListDto {

    public long id;
    public UUID codigo;
    public BigDecimal valorDesconto;
    public BigDecimal valorTotal;
    public String nome;
    public BigDecimal valor;
    public BigDecimal custo;
}
