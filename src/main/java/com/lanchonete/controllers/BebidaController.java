package com.lanchonete.controllers;

import com.lanchonete.apllication.dto.bebida.BebidaListDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.services.bebida.BebidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/bebida")
public class BebidaController extends AbstractBaseController {

    private final BebidaService _service;

    @Autowired
    public BebidaController(BebidaService service) {
        _service = service;
    }

    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ProdutoDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<BebidaListDto>> list(@RequestParam(name = "page") int page) {

        Page<BebidaListDto> list = this._service.listDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Bebida entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity, ProdutoDto.class));
    }
}