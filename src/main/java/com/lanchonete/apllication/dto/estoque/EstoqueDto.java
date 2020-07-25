package com.lanchonete.apllication.dto.estoque;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto  {

    public long id;
    public long idProduto;
    public long quantidade;
    public Calendar data;
}
