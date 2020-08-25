package com.lanchonete.apllication.dto.pedido;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lanchonete.apllication.configurations.MoneyConverter;
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
    @Size(max = 30, message = MessageError.MAX_LIMITE)
    public String dataCadastro;

    @Size(max = 150, message = MessageError.MAX_LIMITE)
    public String codigo;

    @NotNull(message =  MessageError.IS_MANDATORY+"N")
    @DecimalMax(value = "100000000000.00", message = MessageError.MAX_LIMITE)
    @JsonDeserialize(using = MoneyConverter.Deserialize.class)
    public BigDecimal valor;

    @NotNull(message =  MessageError.IS_MANDATORY+"N")
    @DecimalMax(value = "100000000000.00", message = MessageError.MAX_LIMITE)
    @JsonDeserialize(using = MoneyConverter.Deserialize.class)
    public BigDecimal valorDesconto;

    @NotNull(message =  MessageError.IS_MANDATORY+"N")
    @DecimalMax(value = "100000000000.00", message = MessageError.MAX_LIMITE)
    @JsonDeserialize(using = MoneyConverter.Deserialize.class)
    public BigDecimal valorTotal;
    
    public boolean cancelado;

    public String dataCancelado;

    @NotNull(message = MessageError.IS_MANDATORY)
    public EnumEstadoPedido estado;

    @NotNull(message = MessageError.IS_MANDATORY)
    public ClienteGenericDto cliente;

    @Size(min = 1, message = MessageError.IS_MANDATORY)
    public List<PedidoItemDto> pedidoitens;
}
