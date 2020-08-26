package com.lanchonete.apllication.dto.venda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaListDto {

    public long id;
    public String dataCadastro;
    public boolean ativo;
    
    public String pedidoCodigo;

    public String valor;

    public String valorTotal;

    public boolean cancelado;
}
