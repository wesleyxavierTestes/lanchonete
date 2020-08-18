package com.lanchonete.controllers;

import java.util.Objects;

import javax.validation.Valid;

import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.estoque.EstoqueSaida;
import com.lanchonete.domain.entities.produto.Produto;
import com.lanchonete.domain.services.estoque.EstoqueService;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.utils.MessageError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/estoque")
public class EstoqueController extends AbstractBaseController {

    private final EstoqueService _serviceEstoque;
    private final ProdutoService _serviceProduto;

    @Autowired
    public EstoqueController(EstoqueService serviceEstoque, ProdutoService serviceProduto) {
        _serviceEstoque = serviceEstoque;
        _serviceProduto = serviceProduto;
    }

    @GetMapping("novo")
    public ResponseEntity<Object> novo() {
        return ResponseEntity.ok(new EstoqueDto());
    }

    @GetMapping("list")
    public ResponseEntity<Page<EstoqueListDto>> list(@RequestParam(name = "page") int page) {
        
        Page<EstoqueListDto> list = this._serviceEstoque.listDto(page);
        
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/entrance")
    public ResponseEntity<Object> listEntrance(@RequestParam(name = "page") int page) {
        
        Page<EstoqueListDto> list = this._serviceEstoque.listEntrance(page);
        
        return ResponseEntity.ok(list);
    }


    @GetMapping("list/leave")
    public ResponseEntity<Object> findLeave(@RequestParam(name = "page") int page) {
        
        Page<EstoqueListDto> list = this._serviceEstoque.listLeave(page);
        
        return ResponseEntity.ok(list);
    }

    @GetMapping("find")
    public ResponseEntity<Object> find(@RequestParam(name = "id") long id) {
        
        AbstractEstoque entity = this._serviceEstoque.find(id);

        return ResponseEntity.ok(Mapper.map(entity, AbstractEstoque.class));
    }

    @PostMapping("save/add")
    public ResponseEntity<Object> saveAdicionar(@RequestBody() @Valid EstoqueDto entityDto) {

        Produto produto = this._serviceProduto.findByEstoque(entityDto.produto.id);
        if (!Objects.nonNull(produto))
            return ResponseEntity.badRequest().body(MessageError.PRODUTO_EXISTS);

        EstoqueEntrada entity = (EstoqueEntrada) this._serviceEstoque
                .configureSave(Mapper.map(entityDto, EstoqueEntrada.class), produto);

        entity = (EstoqueEntrada) this._serviceEstoque.save(entity);

        return ResponseEntity.ok(entity);
    }

    @PostMapping("save/remove")
    public ResponseEntity<Object> saveRemover(@RequestBody() @Valid EstoqueDto entityDto) {

        Produto produto = this._serviceProduto.findByEstoque(entityDto.produto.id);
        if (!Objects.nonNull(produto))
            return ResponseEntity.badRequest().body(MessageError.PRODUTO_EXISTS);

        if (produto.getEstoqueAtual() <= 0)
            return ResponseEntity.badRequest().body(MessageError.PRODUTO_STOCK_EMPTY);

        EstoqueSaida entity = (EstoqueSaida) this._serviceEstoque
                .configureSave(Mapper.map(entityDto, EstoqueSaida.class), produto);

        entity = (EstoqueSaida) this._serviceEstoque.save(entity);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam(name = "id") long id) {
        
        AbstractEstoque entity = this._serviceEstoque.delete(id);

        return ResponseEntity.ok(Mapper.map(entity));
    }

}