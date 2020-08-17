package com.lanchonete.apllication.dto.venda;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDto {

    public long id;   
    public boolean ativo;
    
    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorDesconto;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valor;

    @NotNull(message = MessageError.IS_MANDATORY)
    public String valorTotal;

    @NotNull(message = MessageError.IS_MANDATORY)
    public List<VendaItemDto> vendaItens;
}
