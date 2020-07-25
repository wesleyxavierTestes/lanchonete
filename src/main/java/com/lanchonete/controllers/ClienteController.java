package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.services.cliente.ClienteService;

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
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService _service;

    @Autowired
    public ClienteController(ClienteService service) {
        _service = service;
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("list/spendmore")
    public ResponseEntity<Page<Cliente>> listSpendMore(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(this._service.listSpendMore(page));
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("list")
    public ResponseEntity<Page<ClienteListDto>> list(@RequestParam(name = "page") int page) {
        Page<ClienteListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Cliente> find(@RequestParam(name = "id") long id) {
        Cliente entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(entity);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() ClienteDto entityDto) {

        Cliente entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();
        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ClienteDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() ClienteDto entityDto) {

        Cliente entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().build();

        entity = Mapper.map(entityDto, entity);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ClienteDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Cliente entity = this._service.find(id);

        entity.setAtivo(true);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ClienteDto.class));
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {
        Cliente entity = this._service.find(id);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ClienteDto.class));
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("save/default")
    public ResponseEntity<ClienteDto> saveDefault(@RequestBody() ClienteDefaultDto entityDto) {

        boolean clienteDefaultExiste = this._service.existeClientePadrao();

        if (clienteDefaultExiste)
            return ResponseEntity.badRequest().build();

        Cliente entity = Mapper.map(entityDto, Cliente.class);

        this._service.createClienteDefault(entity);

        entity = this._service.save(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity, ClienteDto.class));
        return ResponseEntity.badRequest().build();
    }
}