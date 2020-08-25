package com.lanchonete.domain.services.combo;

import java.util.ArrayList;
import java.util.List;
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
import com.lanchonete.infra.repositorys.bebida.IBebidaRepository;
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
    private IBebidaRepository _bebidaRepository;

    @Autowired
    public ComboService(IComboRepository repository) {
        super(repository);
    }

    public Page<ComboListDto> listFilterDto(Combo entity, int page) {
        return super.listFilter(entity, page).map(Mapper.pageMap(ComboListDto.class));
    }

    public Page<ComboListDto> listDto(int page) {
        Page<Combo> findAll = _repository.findAll(PageRequest.of((page - 1), 10));
        return findAll.map(Mapper.pageMap(ComboListDto.class));
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
        List<Lanche> lanches = new ArrayList<>();
        for (Lanche lanche : entity.getLanches()) {
            lanche = this._lancheRepository.findByIdEqualsAndAtivoTrue(lanche.getId());

            if (!Objects.nonNull(lanche))
                throw new RegraNegocioException("lanche" + MessageError.NOT_EXISTS);

            lanches.add(lanche);
        }
        entity.setLanches(lanches);
    }

    private void configurarBebida(Combo entity) {
        List<Bebida> bebidas = new ArrayList<>();
        for (Bebida bebida : entity.getBebidas()) {
            bebida = this._bebidaRepository.findByIdEqualsAndAtivoTrue(bebida.getId());

            if (!Objects.nonNull(bebida))
                throw new RegraNegocioException("bebida" + MessageError.NOT_EXISTS);

            bebidas.add(bebida);
        }
        entity.setBebidas(bebidas);
    }
}