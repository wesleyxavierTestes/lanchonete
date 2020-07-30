package com.lanchonete.apllication.dto.pedido;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemDto  {

    public long id;
    public boolean ativo;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String  nome;
    
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    public EnumEstadoPedido estado;
}