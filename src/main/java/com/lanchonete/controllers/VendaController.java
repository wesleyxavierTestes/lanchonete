package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.Validations;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.venda.VendaService;
import com.lanchonete.utils.MessageError;

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
@RequestMapping("api/venda")
public class VendaController {

    private final VendaService _service;

    @Autowired
    private Validations validations;

    @Autowired
    public VendaController(VendaService service) {
        _service = service;

    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new VendaDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<VendaListDto>> list(@RequestParam(name = "page") int page) {
        Page<VendaListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/cancel")
    public ResponseEntity<Page<VendaListDto>> listCancel(@RequestParam(name = "page") int page) {
        Page<VendaListDto> list = this._service.listCancelDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Venda entity = this._service.find(id);
        
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() VendaDto entityDto) {

        Venda entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity = this._service.save(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("cancel")
    public ResponseEntity<Object> cancel(@RequestParam(name = "id") long id) {
        Venda entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }
}