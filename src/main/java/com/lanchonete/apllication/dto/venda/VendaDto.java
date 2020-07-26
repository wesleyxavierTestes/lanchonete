package com.lanchonete.apllication.dto.venda;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDto  {

    public long id;
    public LocalDateTime dataCadastro;
    public boolean ativo;
    public String nome;
}
