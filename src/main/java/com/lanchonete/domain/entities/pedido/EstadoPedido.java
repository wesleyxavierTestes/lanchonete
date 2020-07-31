package com.lanchonete.domain.entities.pedido;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    @Enumerated(EnumType.STRING)
    private EnumEstadoPedido estado = EnumEstadoPedido.Novo;
}