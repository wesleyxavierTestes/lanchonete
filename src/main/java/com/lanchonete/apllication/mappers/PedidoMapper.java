package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.domain.entities.pedido.Pedido;

final class PedidoMapper {
    private PedidoMapper() {}
    public static Pedido update(PedidoDto entityDto, Pedido entity) {
        return entity;
    }
}