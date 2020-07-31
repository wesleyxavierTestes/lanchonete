package com.lanchonete.controllers;

import java.util.UUID;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.dto.produto.ProdutoListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.domain.services.estoque.EstoqueService;
import com.lanchonete.domain.services.produto.ProdutoService;

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
@RequestMapping("/api/produto")
public class ProdutoController extends AbstractBaseController {

    private final ProdutoService _service;
    private final EstoqueService _serviceEstoque;
    private final CategoriaService _serviceCategoria;

    @Autowired
    public ProdutoController(ProdutoService service, EstoqueService serviceEstoque, CategoriaService serviceCategoria) {
        _service = service;
        _serviceEstoque = serviceEstoque;
        _serviceCategoria = serviceCategoria;
    }

    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new ProdutoDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<ProdutoListDto>> list(@RequestParam(name = "page") int page) {

        Page<ProdutoListDto> list = this._service.listDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/estoque/zero")
    public ResponseEntity<Page<ProdutoListDto>> listEstoqueZero(@RequestParam(name = "page") int page) {

        Page<ProdutoListDto> list = this._service.listEstoqueZero(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/estoque")
    public ResponseEntity<Page<ProdutoListDto>> listEstoque(@RequestParam(name = "page") int page) {

        Page<ProdutoListDto> list = this._service.listEstoque(page);

        return ResponseEntity.ok(list);
    }

    @PostMapping("list/filter")
    public ResponseEntity<Page<ProdutoListDto>> listFilter(@RequestParam(name = "page") int page,
            @RequestBody ProdutoDto filter) {

        Page<ProdutoListDto> list = this._service.listFilterDto(Mapper.map(filter), page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/active")
    public ResponseEntity<Page<ProdutoListDto>> listActive(@RequestParam(name = "page") int page) {

        Page<ProdutoListDto> list = this._service.listActiveDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("list/desactive")
    public ResponseEntity<Page<ProdutoListDto>> listDesactive(@RequestParam(name = "page") int page) {

        Page<ProdutoListDto> list = this._service.listDesactiveDto(page);

        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {

        Produto entity = this._service.find(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody() @Valid ProdutoDto entityDto) {
        Categoria categoria = this._serviceCategoria.find(entityDto.categoria.id);

        Produto entity = Mapper.map(entityDto);

        entity.setCodigo(UUID.randomUUID());
        entity.setCategoria(categoria);

        this._service.save(entity);

        EstoqueEntrada estoqueEntrada = EstoqueEntrada.ProdutoSave(entity);
        this._serviceEstoque.save(estoqueEntrada);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@RequestBody() @Valid ProdutoDto entityDto) {

        Categoria categoria = this._serviceCategoria.find(entityDto.categoria.id);

        Produto entity = this._service.find(entityDto.id);

        Produto map = Mapper.map(entityDto, entity);

        entity.setCategoria(categoria);

        this._service.update(map);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("active")
    public ResponseEntity<Object> active(@RequestParam(name = "id") long id) {

        Produto entity = this._service.ative(id, true);

        return ResponseEntity.ok(Mapper.map(entity));
    }

    @DeleteMapping("desactive")
    public ResponseEntity<Object> desactive(@RequestParam(name = "id") long id) {

        Produto entity = this._service.ative(id, false);

        return ResponseEntity.ok(Mapper.map(entity));
    }

}