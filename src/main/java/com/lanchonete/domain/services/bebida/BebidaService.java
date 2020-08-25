package com.lanchonete.domain.services.bebida;

import java.util.Optional;

import com.lanchonete.apllication.dto.bebida.BebidaListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.bebida.IBebidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BebidaService extends BaseService<Bebida, IBebidaRepository> {

    @Autowired
    public BebidaService(IBebidaRepository repository) {
        super(repository);
    }

    public Bebida find(long id) {
        Optional<Bebida> entity = _repository.findAll().stream().filter(c -> c.getId() == id).findFirst();
        if (!entity.isPresent())
            throw new RegraNegocioException("Item inexistente");
        return entity.get();
    }

    public Page<BebidaListDto> listFilterDto(Bebida entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(BebidaListDto.class));
    }

    public Page<BebidaListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(BebidaListDto.class));
    }
}