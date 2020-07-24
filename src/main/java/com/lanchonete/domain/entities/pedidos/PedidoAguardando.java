package com.lanchonete.domain.entities.pedidos;

import javax.persistence.Entity;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PedidoAguardando extends Pedido {

    @Override
    public PedidoAguardando configurar(EnumEstadoPedido estado) {
        this.setEstado(estado);
        return this;
    }
}