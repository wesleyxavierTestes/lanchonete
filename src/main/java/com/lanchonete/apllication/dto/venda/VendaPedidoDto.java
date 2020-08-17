package com.lanchonete.apllication.dto.venda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaPedidoDto {

    public long id;
    public String codigo;
    
}