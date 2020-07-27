package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.combo.Combo;
import com.lanchonete.domain.services.combo.ComboService;
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
@RequestMapping("api/combo")
public class ComboController extends AbstractBaseController {

    private final ComboService _service;

    @Autowired
    public ComboController(ComboService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ComboDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ComboListDto>> list(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/name")
    public ResponseEntity<Page<ComboListDto>> listByName(
        @RequestParam(name = "page") int page,   
        @RequestParam(name = "name") String name) {
        Page<ComboListDto> list = this._service.listByName(name, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<ComboListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<ComboListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Combo entity = this._service.find(id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid ComboDto entityDto) {

        Combo entity = this._service.save(Mapper.map(entityDto));

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }
    
    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Combo entity = this._service.find(id);

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
        Combo entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        this._service.update(entity);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.ERROS_DATABASE);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}