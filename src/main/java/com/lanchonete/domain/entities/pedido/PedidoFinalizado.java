package com.lanchonete.domain.entities.pedido;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PedidoFinalizado extends Pedido {
    
    @Override
    public PedidoFinalizado configurar() {
        this.configurarEstadoPedido(EnumEstadoPedido.Finalizado);
        this.setDataFinalizacao(LocalDateTime.now());
        this.setAtivo(false);
        this.calcularValorTotal();
        return this;
    }
}