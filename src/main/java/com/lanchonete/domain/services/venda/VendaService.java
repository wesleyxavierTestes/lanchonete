package com.lanchonete.domain.services.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.entities.venda.VendaPedido;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.pedido.IPedidoRepository;
import com.lanchonete.infra.repositorys.venda.IVendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VendaService extends BaseService<Venda> {

    private final IVendaRepository _repository;

    @Autowired
    private IPedidoRepository _pedidoRepository;

    @Autowired
    public VendaService(IVendaRepository repository) {
        super(repository);
        _repository = repository;
    }

    public Page<VendaListDto> listFilterDto(Venda entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(VendaListDto.class));
    }

    public Page<VendaListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(VendaListDto.class));
    }

    public Page<VendaListDto> listCancelDto(int page) {
        return _repository.listCancel(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(VendaListDto.class));
    }

    public void criarVenda(Venda entity) {
        //
        Set<VendaPedido> vendaItens = new HashSet<>();
        BigDecimal valor = BigDecimal.ZERO;
        for (VendaPedido produto : entity.getVendaItens()) {
            Optional<Pedido> pedidoOptional = _pedidoRepository.findById(produto.getPedido().getId());
            if (pedidoOptional.isEmpty())
                throw new RegraNegocioException("Pedido inexistente");
            Pedido pedido = pedidoOptional.get();

            VendaPedido vendaPedido = new VendaPedido();
            vendaPedido.setAtivo(true);
            vendaPedido.setDataCadastro(LocalDateTime.now());
            vendaPedido.setPedido(pedido);

            valor = valor.add(pedido.getValorTotal());
            vendaItens.add(vendaPedido);
        }
        entity.setVendaItens(vendaItens);
        entity.setValor(valor);
        entity.setValorTotal(entity.getValor().subtract(entity.getValorDesconto()));
    }
}