package com.lanchonete.domain.services.estoque;

import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.estoque.IEstoqueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService extends BaseService<AbstractEstoque> {

    private final IEstoqueRepository _repository;

    @Autowired
    public EstoqueService(IEstoqueRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<EstoqueListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(EstoqueListDto.class));
    }

    public Page<EstoqueListDto> listLeave(int page) {
        return _repository.listLeave(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(EstoqueListDto.class));
    }

    public Page<EstoqueListDto> listEntrance(int page) {
        return _repository.listEntrance(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(EstoqueListDto.class));
    }

    public IEstoque configureSave(IEstoque entity, Produto produto) {
        entity.setProduto(produto);
        entity.configureSave();
        return entity;
    }
}