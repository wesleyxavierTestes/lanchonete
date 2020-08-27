package com.lanchonete.domain.services.lanche;

import java.util.List;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.processadores.LancheProcessaProduto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class LancheService extends BaseService<Lanche, ILancheRepository> {

    @Autowired
    private IProdutoRepository _produtoRepository;

    @Autowired
    public LancheService(ILancheRepository repository) {
        super(repository);
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

        List<IProdutoComposicao> ingredientes = new LancheProcessaProduto(this._produtoRepository)
                                                    .mapperIngrediente(entity); 
        LancheProcessaProduto.setDadosBaseLanche(entity, categoria, ingredientes);
	}
}