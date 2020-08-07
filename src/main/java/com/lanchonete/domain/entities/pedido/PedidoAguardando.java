package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@Entity
public class PedidoAguardando extends Pedido {

    @Override
    public PedidoAguardando configurar() {
        this.configurarEstadoPedido(EnumEstadoPedido.Aguardando);
        return this;
    }
}