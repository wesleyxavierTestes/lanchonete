package com.lanchonete.apllication.dto.pedido;

import java.util.Set;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoListDto {

    public long id;
    public boolean ativo;

    public Set<PedidoItemDto> pedidoitens;

    public String codigo;

    public String valor;
    public String valorDesconto;
    public String valorTotal;
    
    public boolean cancelado;
    public String dataCancelado;

    public EnumEstadoPedido estado;
    
    public ClienteDefaultDto cliente;
}
