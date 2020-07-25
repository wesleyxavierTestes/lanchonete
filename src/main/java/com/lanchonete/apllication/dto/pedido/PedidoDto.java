package com.lanchonete.apllication.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto  {

    public long id;
    public String nome;
}
