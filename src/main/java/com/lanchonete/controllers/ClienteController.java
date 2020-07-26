package com.lanchonete.controllers;

import java.util.Objects;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.utils.HttpBase;
import com.lanchonete.utils.MessageError;
import com.lanchonete.utils.UrlConstants;
import com.lanchonete.apllication.validations.Validations;

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
    private Validations validations;

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

    @GetMapping("cep")
    public ResponseEntity<Object> getMethodName(@RequestParam String cep) {
        try {
            HttpBase.HttpGet(UrlConstants.getViaCep(cep), EnderecoDto.class);
            return ResponseEntity.badRequest().body("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EnderecoDto());
        }
    }

    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    // TODO: INCOMPLETO
    // TODO: NECESSITA DE TESTES
    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ClienteDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ClienteListDto>> list(@RequestParam(name = "page") int page) {
        Page<ClienteListDto> list = this._service.listDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("lis/active")
    public ResponseEntity<Page<ClienteListDto>> listActive(@RequestParam(name = "page") int page) {
        Page<ClienteListDto> list = this._service.listActiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("lis/desactive")
    public ResponseEntity<Page<ClienteListDto>> listDesactive(@RequestParam(name = "page") int page) {
        Page<ClienteListDto> list = this._service.listDesactiveDto(page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        Cliente entity = this._service.find(id);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() ClienteDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Cliente entity = Mapper.map(entityDto);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity = this._service.save(entity);
        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() ClienteDto entityDto) {
        if (!validations.by(entityDto).isValid())
            return ResponseEntity.badRequest().body(validations.getErros());

        Cliente entity = this._service.find(entityDto.id);
        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        this._service.update(Mapper.map(entityDto, entity));

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {
        Cliente entity = this._service.find(id);

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
        Cliente entity = this._service.find(id);

        if (!Objects.nonNull(entity))
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        entity.setAtivo(false);
        entity = this._service.update(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("save/default")
    public ResponseEntity<Object> saveDefault(@RequestBody() ClienteDefaultDto entityDto) {

        boolean clienteDefaultExiste = this._service.existeClientePadrao();

        if (clienteDefaultExiste)
            return ResponseEntity.badRequest().body("");

        Cliente entity = Mapper.map(entityDto, Cliente.class);

        this._service.createClienteDefault(entity);

        entity = this._service.save(entity);

        if (Objects.nonNull(entity))
            return ResponseEntity.ok(Mapper.map(entity));

        return ResponseEntity.badRequest().body("");
    }
}