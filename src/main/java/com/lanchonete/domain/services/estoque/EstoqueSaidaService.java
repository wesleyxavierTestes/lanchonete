package com.lanchonete.domain.services.estoque;

import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.estoque.IEstoqueSaidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EstoqueSaidaService extends BaseService<EstoqueSaida, IEstoqueSaidaRepository> {
	
    @Autowired
    public EstoqueSaidaService(final IEstoqueSaidaRepository repository) {
        super(repository);
    }

    public Page<EstoqueListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(EstoqueListDto.class));
    }
}