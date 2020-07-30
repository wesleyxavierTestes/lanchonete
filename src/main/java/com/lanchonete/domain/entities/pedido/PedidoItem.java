package com.lanchonete.domain.entities.pedido;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.produto.baseentity.AbstractProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pedido_item")
public class PedidoItem extends AbstractProduto implements IProdutoCardapio, IProdutoPedido {

    private EnumEstadoPedido estado = EnumEstadoPedido.Novo;
    
    private UUID codigo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Pedido pedido;
   
}