package com.lanchonete.apllication.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaListDto {
    public long id;
    public boolean ativo;
    public String nome;
}
