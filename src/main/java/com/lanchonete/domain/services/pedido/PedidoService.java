package com.lanchonete.domain.services.pedido;

import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.pedido.IPedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends BaseService<Pedido> {

    private final IPedidoRepository _repository;

    @Autowired
    public PedidoService(IPedidoRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<PedidoListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(PedidoListDto.class));
    }

	public Page<PedidoListDto> listCancelDto(int page) {
        return _repository.listCancel(PageRequest.of((page - 1), 10))
        .map(Mapper.pageMap(PedidoListDto.class));
	}
}