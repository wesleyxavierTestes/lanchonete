package com.lanchonete.domain.services.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.entities.venda.VendaItem;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.categoria.ICategoriaRepository;
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

    public void criarVenda(Venda entity, Categoria categoria) {
        List<VendaItem> vendaItens = new ArrayList<>();
        BigDecimal valor = criarVendaConfigureVendaItens(entity, vendaItens, categoria);
        criarVendaSetVenda(entity, vendaItens, valor);
    }

    /**
     * Configura lista de Venda itens e retorna o cliente de pedido
     * 
     * @param entity
     * @param vendaItens
     * @param valor
     * @return
     */
    private BigDecimal criarVendaConfigureVendaItens(Venda entity, 
    List<VendaItem> vendaItens, Categoria categoria) {
        BigDecimal valor = BigDecimal.ZERO;
        for (VendaItem _vendaPedido : entity.getVendaItens()) {
            criarVendaGetPedidoRepositoryById(_vendaPedido);
            criarVendaGetClientePedido(entity, _vendaPedido.getPedido());
            valor = criarVendaSetVendaItem(valor, _vendaPedido, categoria);
            vendaItens.add(_vendaPedido);
        }
        return valor;
    }

    private void criarVendaGetClientePedido(Venda entity, Pedido pedido) {
        if (!Objects.nonNull(entity.getCliente()))
           entity.setCliente(pedido.getCliente());
    }

    private void criarVendaSetVenda(Venda entity, List<VendaItem> vendaItens, BigDecimal valor) {
        entity.setVendaItens(vendaItens);
        entity.setValor(valor);
        entity.setValorTotal(entity.getValor().subtract(entity.getValorDesconto()));
    }

    private BigDecimal criarVendaSetVendaItem(BigDecimal valor, VendaItem _vendaPedido, Categoria categoria) {
        _vendaPedido.setNome(_vendaPedido.getPedido().getCodigo().toString());
        _vendaPedido.setAtivo(true);
        _vendaPedido.setDataCadastro(LocalDateTime.now());
        _vendaPedido.setCategoria(categoria);
        return valor.add(_vendaPedido.getPedido().getValorTotal());
    }

    private void criarVendaGetPedidoRepositoryById(VendaItem _vendaPedido) {
        Pedido pedido = _vendaPedido.getPedido();
        Optional<Pedido> pedidoOptional = _pedidoRepository.findById(pedido.getId());
        if (!pedidoOptional.isPresent())
            throw new RegraNegocioException("Pedido inexistente");

        pedido = pedidoOptional.get();
        _vendaPedido.setPedido(pedido);
    }

}