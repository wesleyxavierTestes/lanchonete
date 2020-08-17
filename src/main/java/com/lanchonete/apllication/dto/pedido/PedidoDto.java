package com.lanchonete.apllication.dto.pedido;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.cliente.ClienteGenericDto;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto  {

    public long id;
    public boolean ativo;
    @Max(value = 30, message = MessageError.MAX_LIMITE)
    public String dataCadastro;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorDesconto;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorTotal;
    
    @NotNull(message = MessageError.IS_MANDATORY)
    public boolean cancelado;

    @Max(value = 30, message = MessageError.MAX_LIMITE)
    public String dataCancelado;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumEstadoPedido estado;

    @NotNull(message = MessageError.IS_MANDATORY)
    public ClienteGenericDto cliente;

    @NotNull(message = MessageError.IS_MANDATORY)
    public List<PedidoItemDto> pedidoitens;
}
