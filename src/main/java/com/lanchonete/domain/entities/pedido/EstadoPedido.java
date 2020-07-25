package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;

import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EstadoPedido extends BaseEntity {
    private EnumEstadoPedido estado = EnumEstadoPedido.Novo;
}