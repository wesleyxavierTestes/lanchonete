package com.lanchonete.apllication.dto.estoque;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto  {

    public long id;
    public long quantidade;
    public LocalDateTime data;

    public EstoqueProdutoDto produto;
}
