package com.lanchonete.domain.services.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.pedido.IPedidoState;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.pedido.PedidoAguardando;
import com.lanchonete.domain.entities.pedido.PedidoItem;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoCardapio;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoPedido;
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

    public Page<PedidoListDto> listFilterDto(Pedido entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listCancelDto(int page) {
        return _repository.listCancel(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listActive(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listDay(int page) {
        LocalDateTime data = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        return _repository.listDay(data.toString(), PageRequest.of((page - 1), 10))
                .map(Mapper.pageMap(PedidoListDto.class));
    }

    public Page<PedidoListDto> listClient(long id, int page) {
        return _repository.listClient(id, PageRequest.of((page - 1), 10)).map(Mapper.pageMap(PedidoListDto.class));
    }

    public PedidoAguardando configurarPedido(Pedido entity, Cardapio cardapio, Cliente cliente) {
        entity.setValor(BigDecimal.ZERO);
        configurarPedidoItens(entity, cardapio);
        entity.calcularValorTotal();
        entity.setCodigo(UUID.randomUUID());
        entity.setCliente(cliente);
        PedidoAguardando fazerPedido = (PedidoAguardando)entity.fazerPedido();
        return fazerPedido;
    }

    private void configurarPedidoItens(Pedido entity, Cardapio cardapio) {
        try {
            for (IProdutoPedido produto : entity.getPedidoitens()) {
                for (IProdutoCardapio produtoCardapio : cardapio.getItensDisponiveis()) {
                    if (produto.getCodigo().equals(produtoCardapio.getCodigo())) {
                        produto = (IProdutoPedido) produtoCardapio;
                        entity.setValor(entity.getValor().add(produto.getValor()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}