package com.lanchonete.domain.services.combo;

import com.lanchonete.domain.entities.cardapio.combo.Combo;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.combo.IComboRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComboService extends BaseService<Combo> {

    private final IComboRepository _repository;

    @Autowired
    public ComboService(IComboRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<Combo> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }
}