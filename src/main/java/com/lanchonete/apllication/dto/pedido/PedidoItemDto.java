package com.lanchonete.apllication.dto.pedido;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
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
    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String nome;

    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String codigo;
    
    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumTipoProduto tipoProduto;
}