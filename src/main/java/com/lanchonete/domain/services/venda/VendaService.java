package com.lanchonete.domain.services.venda;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.venda.IVendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VendaService extends BaseService<Venda> {

    private final IVendaRepository _repository;

    @Autowired
    public VendaService(IVendaRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<Venda> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }

    public Page<VendaListDto> listDto(int page) {
        return _repository.findAllDto(PageRequest.of((page - 1), 10));
    }
}