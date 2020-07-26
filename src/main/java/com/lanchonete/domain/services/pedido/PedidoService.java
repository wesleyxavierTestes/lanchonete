package com.lanchonete.domain.services.pedido;

import com.lanchonete.apllication.dto.pedido.PedidoListDto;
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

    public Page<Pedido> listFilter(int page) {
        return this._repository.findAll(PageRequest.of((page - 1), 10));
    }

    public Page<PedidoListDto> listDto(int page) {
        return _repository.findAllDto(PageRequest.of((page - 1), 10));
    }

	public Page<PedidoListDto> listCancelDto(int page) {
		return this._repository.listCancelDto(PageRequest.of((page - 1), 10));
	}
}