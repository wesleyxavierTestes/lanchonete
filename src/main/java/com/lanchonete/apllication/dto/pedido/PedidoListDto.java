package com.lanchonete.apllication.dto.pedido;

import java.util.List;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
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

    public List<PedidoItemDto> pedidoitens;

    public String codigo;

    public String valor;
    public String valorDesconto;
    public String valorTotal;
    public ClienteDefaultDto cliente;

    public EnumEstadoPedido estado;
    
    public boolean cancelado;
    public String dataCancelado;
}
