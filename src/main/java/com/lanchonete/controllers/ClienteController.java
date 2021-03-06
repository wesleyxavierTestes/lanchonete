package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/cliente")
public class ClienteController extends AbstractBaseController {

    private final ClienteService _service;

    @Autowired
    private HttpBase httpBase;

    @Autowired
    public ClienteController(ClienteService service) {
        _service = service;
    }

    @GetMapping("list/spendmore")
    public ResponseEntity<Page<ClienteListDto>> listSpendMore(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok(this._service.listSpendMore(page));
    }

    @GetMapping("cep")
    public ResponseEntity<Object> getMethodName(@RequestParam String cep) {
        try {
            httpBase.HttpGet(UrlConstants.getViaCep(cep), EnderecoDto.class);
            return ResponseEntity.badRequest().body(" ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EnderecoDto());
        }
    }

    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ClienteDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ClienteListDto>> list(@RequestParam(name = "page") int page) {

        Page<ClienteListDto> list = this._service.listDto(page);

        return ResponseEntity.ok(list);
    }

    @PostMapping("list/filter")
    public ResponseEntity<Page<ClienteListDto>> listFilter(@RequestParam(name = "page") int page,
            @RequestBody ClienteDto filter) {

        Page<ClienteListDto> list = this._service.listFilterDto(Mapper.map(filter), page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<ClienteListDto>> listActive(@RequestParam(name = "page") int page) {

        Page<ClienteListDto> list = this._service.listActiveDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<ClienteListDto>> listDesactive(@RequestParam(name = "page") int page) {

        Page<ClienteListDto> list = this._service.listDesactiveDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Cliente entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @GetMapping("find/default")
    public ResponseEntity<Object> findefault() {

        Cliente entity = this._service.findDefault();

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid ClienteDto entityDto) {
        Object isValid = Cliente.isValidCpfCnpj(entityDto.tipoPessoa, entityDto.cpf, entityDto.cnpj);
        if (Objects.nonNull(isValid)) ResponseEntity.badRequest().body(isValid);

        Cliente entity = Mapper.map(entityDto);

        this._service.save(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() @Valid ClienteDto entityDto) {
        Object isValid = Cliente.isValidCpfCnpj(entityDto.tipoPessoa, entityDto.cpf, entityDto.cnpj);
        if (Objects.nonNull(isValid)) ResponseEntity.badRequest().body(isValid);

        Cliente entity = this._service.find(entityDto.id);

        this._service.update(Mapper.map(entityDto, entity));

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {

        Cliente entity = this._service.ative(id, true);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {

        Cliente entity = this._service.ative(id, false);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save/default")
    public ResponseEntity<Object> saveDefault(@RequestBody() ClienteDefaultDto entityDto) {

        boolean clienteDefaultExiste = this._service.existeClientePadrao();

        if (clienteDefaultExiste)
            return ResponseEntity.badRequest().body(MessageError.NOT_EXISTS);

        Cliente entity = Mapper.map(entityDto, Cliente.class);

        this._service.createClienteDefault(entity);

        this._service.save(entity);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}