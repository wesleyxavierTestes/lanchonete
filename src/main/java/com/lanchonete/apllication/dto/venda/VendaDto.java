package com.lanchonete.apllication.dto.venda;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.lanchonete.apllication.dto.cliente.ClienteGenericDto;
import com.lanchonete.utils.MessageError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDto  {

    public long id;

    @Max(value = 30, message = MessageError.MAX_LIMITE)
    public String dataCadastro;

    public boolean ativo;


  public Set<IProdutoVenda> pedidos = new HashSet<>();


    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String valorDesconto;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String valor;

    @NotNull(message = MessageError.IS_MANDATORY)
    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String valorTotal;

    @NotNull(message = MessageError.IS_MANDATORY)
    public boolean cancelado;

    @Max(value = 150, message = MessageError.MAX_LIMITE)
    public String dataCancelado;


    @NotNull(message = MessageError.IS_MANDATORY)
    public ClienteGenericDto cliente;
}
