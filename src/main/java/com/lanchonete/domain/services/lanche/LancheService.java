package com.lanchonete.domain.services.lanche;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LancheService extends BaseService<Lanche> {

    private final ILancheRepository _repository;

    @Autowired
    private IProdutoRepository _produtoRepository;

    @Autowired
    public LancheService(ILancheRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<LancheListDto>  listFilterDto(Lanche entity, int page) {
        return super. listFilter(entity, page).map(Mapper.pageMap(LancheListDto.class));
    }

    public Page<LancheListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(LancheListDto.class));
    }

    public Page<LancheListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(LancheListDto.class));
    }

    public Page<LancheListDto> listDesactiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(LancheListDto.class));
    }

	public void criarLanche(Lanche entity, Categoria categoria) {

        entity.setCodigo(UUID.randomUUID());
        entity.setCategoria(categoria);

        for (IProdutoComposicao ingrediente : entity.getIngredientesLanche()) {
            Set<IProdutoComposicao> ingredientes = new HashSet<>();
            Produto produto = _produtoRepository.findByIdAtive(ingrediente.getId());
            if (!Objects.nonNull(produto))
                throw new RegraNegocioException(String.format("Produto %s", ingrediente.getNome()) 
                + MessageError.EXISTS);
                produto.setId(0);
                ingredientes.add(Mapper.map(produto, Ingrediente.class));   
        } 

        entity.calcularValor();
	}
}