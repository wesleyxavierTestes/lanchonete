package com.lanchonete.domain.services.estoque;

import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.estoque.IEstoqueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService extends BaseService<AbstractEstoque> {

    private final IEstoqueRepository _repository;

    @Autowired
    public EstoqueService(IEstoqueRepository repository) {
        super(repository);
        _repository = repository;
    }
}