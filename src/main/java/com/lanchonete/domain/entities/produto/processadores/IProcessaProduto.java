package com.lanchonete.domain.entities.produto.processadores;

import com.lanchonete.domain.entities.produto.baseentity.IProduto;

public interface  IProcessaProduto {
    <T extends IProduto> T processar(IProduto produto);
}