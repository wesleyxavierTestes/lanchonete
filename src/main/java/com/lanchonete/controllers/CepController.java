package com.lanchonete.controllers;

import com.lanchonete.domain.entities.utils.Cep;
import com.lanchonete.utils.HttpBase;
import com.lanchonete.utils.Validacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/cep")
public class CepController {

    @GetMapping(path = "consultar/{cep}")
    public ResponseEntity<Cep> getCep(@PathVariable(name = "cep") String cep) {
        if (!Validacao.validarCep(cep)) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        String url = "https://viacep.com.br/ws/{parametro}/json/";

        ResponseEntity<Cep> cepConsultado = HttpBase.getParameterResponseEntity(url, cep, Cep.class);

        return cepConsultado;
    }

    @GetMapping(path = "cnpj/{cnpj}")
    public ResponseEntity<String> getcnpj(@PathVariable(name = "cnpj") String cnpj) {

        String url = "https://www.receitaws.com.br/v1/cnpj/"+cnpj;

        ResponseEntity<String> cepConsultado = HttpBase.getParameterResponseEntity(url, String.class);

        return cepConsultado;
    }

    @GetMapping(path = "consultar/piped/{cep}")
    public ResponseEntity<Object> getCepPiped(@PathVariable(name = "cep") String cep) {
        if (!Validacao.validarCep(cep)) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        String url = "https://viacep.com.br/ws/{parametro}/piped/";

        ResponseEntity<Object> cepConsultado = HttpBase.getParameterResponseEntity(url, cep, Object.class);

        return cepConsultado;
    }
}
