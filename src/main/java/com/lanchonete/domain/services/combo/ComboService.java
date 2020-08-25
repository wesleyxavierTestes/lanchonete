package com.lanchonete.domain.services.combo;

import java.util.Objects;
import java.util.UUID;

import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.services.BaseService;
import com.lanchonete.infra.repositorys.combo.IComboRepository;
import com.lanchonete.infra.repositorys.lanche.ILancheRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComboService extends BaseService<Combo, IComboRepository> {

    @Autowired
    private ILancheRepository _lancheRepository;

    @Autowired
    private IProdutoRepository _produtoRepository;

    @Autowired
    public ComboService(IComboRepository repository) {
        super(repository);
    }

    public Page<ComboListDto> listFilterDto(Combo entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(ComboListDto.class));
    }

    public Page<ComboListDto> listDto(int page) {
        return _repository.findAll(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ComboListDto.class));
    }

    public Page<ComboListDto> listActiveDto(int page) {
        return _repository.listActive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ComboListDto.class));
    }

    public Page<ComboListDto> listDesactiveDto(int page) {
        return _repository.listDesactive(PageRequest.of((page - 1), 10)).map(Mapper.pageMap(ComboListDto.class));
    }

    public void criarCombo(Combo entity, Categoria categoria) {
        configurarBebida(entity);
        configurarLanche(entity);

        entity.setCodigo(UUID.randomUUID());
        entity.setCategoria(categoria);
        entity.calcularValor();
    }

    private void configurarLanche(Combo entity) {
        Lanche lanche = this._lancheRepository.findByIdAtive(entity.getLanche().getId());

        if (!Objects.nonNull(lanche))
            throw new RegraNegocioException("lanche" + MessageError.NOT_EXISTS);

        entity.setLanche(lanche);
    }

    private void configurarBebida(Combo entity) {
        Produto produtoBebida = this._produtoRepository.findByIdAndAtivoIsTrue(entity.getBebida().getId());

        if (!Objects.nonNull(produtoBebida))
            throw new RegraNegocioException("bebida" + MessageError.NOT_EXISTS);

        Bebida bebida = Mapper.map(produtoBebida, Bebida.class);
        bebida.setId(0);
        entity.setBebida(bebida);
    }
}