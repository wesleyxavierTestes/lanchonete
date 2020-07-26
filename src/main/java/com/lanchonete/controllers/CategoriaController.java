package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.categoria.CategoriaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {

    private final CategoriaService _service;

    @Autowired
    private Validations validations;

    @Autowired
    public CategoriaController(CategoriaService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new CategoriaDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<CategoriaListDto>> list(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<CategoriaListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<CategoriaListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<CategoriaListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Categoria entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() CategoriaDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Categoria entity = this._service.save(Mapper.map(entityDto));

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() CategoriaDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Categoria entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Categoria entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(true);
        this._service.update(entity);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Categoria entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        this._service.update(entity);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        Categoria entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity = this._service.delete(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }

}