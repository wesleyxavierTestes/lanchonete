package com.lanchonete.domain.services.venda;

import java.util.Optional;

import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.pedido.IPedidoRepository;
import com.lanchonete.infra.repositorys.venda.IVendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class VendaService extends BaseService<Venda, IVendaRepository> {

    @Autowired
    private IPedidoRepository _pedidoRepository;

    @Autowired
    public VendaService(IVendaRepository repository) {
        super(repository);
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
        criarVendaGetPedidoRepositoryById(entity);
        criarVendaGetClientePedido(entity);
        criarVendaSetValores(entity);
        criarVendaConfigure(entity);
    }

    /**
     * Configura lista de Venda itens e retorna o cliente de pedido
     * 
     * @param entity
     * @param vendaItens
     * @param valor
     * @return
     */
    private void criarVendaConfigure(Venda entity) {
        entity.setAtivo(true);
    }

    private void criarVendaGetClientePedido(Venda entity) {
           entity.setCliente(entity.getPedido().getCliente());
    }

    private void criarVendaSetValores(Venda entity) {
        entity.setValor(entity.getPedido().getValorTotal());
        entity.setValorTotal(entity.getValor().subtract(entity.getValorDesconto()));
    }

    private void criarVendaGetPedidoRepositoryById(Venda venda) {
        Optional<Pedido> pedidoOptional = _pedidoRepository.findById(venda.getPedido().getId());
        
        if (!pedidoOptional.isPresent())
            throw new RegraNegocioException("Pedido inexistente");

        Pedido pedido = pedidoOptional.get();
        venda.setPedido(pedido);
    }

}