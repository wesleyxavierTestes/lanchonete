package com.lanchonete.apllication.dto.pedido;

import javax.validation.constraints.Max;

import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemDto extends AbstractProduto implements IProdutoCardapio {

    public long id;
    public boolean ativo;
    
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    public EnumEstadoPedido estado;
}