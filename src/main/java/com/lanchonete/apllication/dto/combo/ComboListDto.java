package com.lanchonete.apllication.dto.combo;

import java.math.BigDecimal;
import java.util.UUID;

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
