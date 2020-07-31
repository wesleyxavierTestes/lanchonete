package com.lanchonete.apllication.dto.venda;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaItemDto {

    public long id;
    public VendaPedidoDto pedido;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    private String valorDesconto;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    private String valorTotal;
}