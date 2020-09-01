package com.lanchonete.controllers;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.bebida.BebidaListDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.bebida.Bebida;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.domain.services.bebida.BebidaService;
import com.lanchonete.domain.services.produto.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/bebida")
public class BebidaController extends AbstractBaseController {

    private final BebidaService _service;
    private final ProdutoService _serviceProduto;

    @Autowired
    public BebidaController(BebidaService service, ProdutoService serviceProduto) {
        _service = service;
        _serviceProduto = serviceProduto;
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

    @PutMapping("convert")
    public ResponseEntity<Object> convert(@RequestBody() @Valid ProdutoDto entityDto) {

        Produto entityProduto = (Produto)this._serviceProduto.find(entityDto.id);

        Bebida mapEntity = Mapper.map(entityProduto, Bebida.class);
        mapEntity.setTipoProduto(EnumTipoProduto.Bebida);

        Bebida entity = this._service.save(mapEntity);

        return ResponseEntity.ok(Mapper.map(entity));
    }
}