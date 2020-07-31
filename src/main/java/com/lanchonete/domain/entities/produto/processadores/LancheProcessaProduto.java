package com.lanchonete.domain.entities.produto.processadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.ingrediente.Ingrediente;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.entities.produto.baseentity.IProduto;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;
import com.lanchonete.utils.MessageError;

public class LancheProcessaProduto extends ProcessaProduto {

    public LancheProcessaProduto(IProdutoRepository repository) {
      super(repository);
	}

	@Override
    public <T extends IProduto> T processar(IProduto lanche) {
        Produto produto = this.getProduto(lanche.getCodigo());
        
        T itemProduto = (T)Mapper.map(produto, Lanche.class);
        
        return (T)itemProduto;
    }
    
    public boolean validarExisteEstoqueProduto(Lanche lanche) {
        for (IProdutoComposicao produto : lanche.getIngredientesLanche()) {
            if (!validarExisteEstoque(produto))
                return false;
        }
        return true;
    }

    public List<IProdutoComposicao> mapperIngrediente(Lanche entity) {
        List<IProdutoComposicao> ingredientes = new ArrayList<>();
        for (IProdutoComposicao ingrediente : entity.getIngredientesLanche()) {
            Produto produto = _repository.findByIdAtive(ingrediente.getId());
            if (!Objects.nonNull(produto))
                throw new RegraNegocioException(String.format("Produto %s", ingrediente.getNome()) 
                + MessageError.EXISTS);
                Ingrediente ingredienteMapeado = mapperIngrediente(ingrediente, produto);
                ingredientes.add(ingredienteMapeado);
        }
        return ingredientes;
    }

    private Ingrediente mapperIngrediente(IProdutoComposicao ingrediente, Produto produto) {
        Ingrediente _ingrediente = Mapper.map(produto, Ingrediente.class);
        _ingrediente.setId(0);
        _ingrediente.setTipoProduto(EnumTipoProduto.Ingrediente);
        _ingrediente.setCategoria(produto.getCategoria());
        _ingrediente.setCodigo(produto.getCodigo());
        _ingrediente.setValor(ingrediente.getValor());
        _ingrediente.setAtivo(true);

        return _ingrediente;
    }

	public static void setDadosBaseLanche(Lanche entity, Categoria categoria, List<IProdutoComposicao> ingredientes) {
        entity.setIngredientesLanche(ingredientes);
        entity.setCodigo(UUID.randomUUID());
        entity.setCategoria(categoria);
        entity.calcularValor();
        entity.setAtivo(true);
	}    
}