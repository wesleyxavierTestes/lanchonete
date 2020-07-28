package com.lanchonete.domain.services.lanche;

import com.lanchonete.apllication.dto.lanche.LancheListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LancheService extends BaseService<Lanche> {

    private final ILancheRepository _repository;

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
}