package com.lanchonete.domain.services.venda;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.mappers.Mapper;
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

    public Page<VendaListDto>  listFilterDto(Venda entity, int page) {
        return super. listFilter(entity, page).map(Mapper.pageMap(VendaListDto.class));
    }

    public Page<VendaListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(VendaListDto.class));
    }

    public Page<VendaListDto> listCancelDto(int page) {
        return _repository.listCancel(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(VendaListDto.class));
    }
}