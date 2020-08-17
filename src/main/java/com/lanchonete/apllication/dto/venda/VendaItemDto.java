package com.lanchonete.apllication.dto.venda;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaItemDto {

    public long id;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @NotNull(message = MessageError.IS_MANDATORY)
    public VendaPedidoDto pedido;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorDesconto;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorTotal;
}