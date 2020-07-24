package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.services.estoque.EstoqueService;
import com.lanchonete.utils.ModelMapperUtils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estoque")
public class EstoqueController {

    private ModelMapper mapper = ModelMapperUtils.getInstance();

    private final EstoqueService _service;

    @Autowired
    public EstoqueController(EstoqueService service) {
        _service = service;
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("list")
    public ResponseEntity<Page<EstoqueListDto>> list(@RequestParam(name = "page") int page) {
        Page<EstoqueListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("find")
    public ResponseEntity<IEstoque> find(@RequestParam(name = "id") long id) {
        IEstoque entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @PostMapping("save/adicionar")
    public ResponseEntity<Object> saveAdicionar(@RequestBody() EstoqueDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        EstoqueEntrada entrada = new EstoqueEntrada();
        // TODO: Lógica

        EstoqueEntrada entity = (EstoqueEntrada) this._service.save(entrada);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entityDto);
        return ResponseEntity.badRequest().build();
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @PostMapping("save/remover")
    public ResponseEntity<Object> saveRemover(@RequestBody() EstoqueDto entityDto) {
        if (!entityDto.getIsValid())
            return ResponseEntity.badRequest().build();

        EstoqueSaida entrada = new EstoqueSaida();
        // TODO: Lógica
        
        EstoqueSaida entity = (EstoqueSaida) this._service.save(entrada);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, EstoqueDto.class));
        return ResponseEntity.badRequest().build();
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        IEstoque entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();

        entity = this._service.delete(id);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(mapper.map(entity, EstoqueDto.class));
        return ResponseEntity.badRequest().build();
    }

}