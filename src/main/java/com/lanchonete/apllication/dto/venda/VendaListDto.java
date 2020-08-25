package com.lanchonete.apllication.dto.venda;

import java.util.List;

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
    
    public VendaPedidoDto pedido;

    public String valor;

    public String valorTotal;

    public boolean cancelado;
}
