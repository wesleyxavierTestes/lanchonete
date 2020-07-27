package com.lanchonete.domain.services.combo;

import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.apllication.mappers.Mapper;
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

    public Page<ComboListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ComboListDto.class));
    }

	public Page<ComboListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ComboListDto.class));
	}

	public Page<ComboListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ComboListDto.class));
	}

	public Page<ComboListDto> listByName(String name, int page) {
        return _repository.listByName(name, PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(ComboListDto.class));
	}
}