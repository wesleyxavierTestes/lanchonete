package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.estoque.IEstoque;
import com.lanchonete.domain.services.estoque.EstoqueService;

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

    private final EstoqueService _service;

    @Autowired
    public EstoqueController(EstoqueService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new EstoqueDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<EstoqueListDto>> list(@RequestParam(name = "page") int page) {
        Page<EstoqueListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("list/entrance")
    public ResponseEntity<Object> listEntrance(@RequestParam(name = "page") int page) {
        Page<EstoqueListDto> list = this._service.listEntrance(page);
        return ResponseEntity.ok(list);
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("list/leave")
    public ResponseEntity<Object> findLeave(@RequestParam(name = "page") int page) {
        Page<EstoqueListDto> list = this._service.listLeave(page);
        return ResponseEntity.ok(list);
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        IEstoque entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, AbstractEstoque.class));
        return ResponseEntity.badRequest().body("");
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @PostMapping("save/add")
    public ResponseEntity<Object> saveAdicionar(@RequestBody() EstoqueDto entityDto) {

        EstoqueEntrada entity = Mapper.map(entityDto, EstoqueEntrada.class);
        // TODO: Lógica

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity = (EstoqueEntrada) this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entityDto);
        return ResponseEntity.badRequest().body("");
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @PostMapping("save/remove")
    public ResponseEntity<Object> saveRemover(@RequestBody() EstoqueDto entityDto) {

        EstoqueSaida entity = Mapper.map(entityDto, EstoqueSaida.class);
        // TODO: Lógica

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity = (EstoqueSaida) this._service.save(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, EstoqueDto.class));
        return ResponseEntity.badRequest().body("");
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        IEstoque entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body("");

        entity = this._service.delete(id);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, EstoqueDto.class));
        return ResponseEntity.badRequest().body("");
    }

}