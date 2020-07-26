package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.Validations;
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
public class ComboController {

    private final ComboService _service;

    @Autowired
    private Validations validations;

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

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Combo entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));
        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("lis/active")
    public ResponseEntity<Page<ComboListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("lis/desactive")
    public ResponseEntity<Page<ComboListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ComboListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() ComboDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Combo entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);
        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));
        return ResponseEntity.badRequest().body("");
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() ComboDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Combo entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Combo entity = this._service.find(id);

        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Combo entity = this._service.find(id);

        if (!Objects.nonNull(entity)) 
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

}