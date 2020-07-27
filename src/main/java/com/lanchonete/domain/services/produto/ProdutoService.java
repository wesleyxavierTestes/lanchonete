package com.lanchonete.domain.services.produto;

import com.lanchonete.apllication.dto.produto.ProdutoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends BaseService<Produto> {

    private final IProdutoRepository _repository;

    @Autowired
    public ProdutoService(IProdutoRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<Produto> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }

    public Page<ProdutoListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ProdutoListDto.class));
    }

    public Page<ProdutoListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ProdutoListDto.class));
    }

	public Page<ProdutoListDto> listByName(String name, int page) {
        return _repository.listByName(name, PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ProdutoListDto.class));
	}

	public Page<ProdutoListDto> listEstoqueZero(int page) {
		return _repository.listEstoqueZero(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ProdutoListDto.class));
	}
}