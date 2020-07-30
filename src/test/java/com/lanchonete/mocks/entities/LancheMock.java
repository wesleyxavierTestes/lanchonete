package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.utils.ObjectMapperUtils;
import com.lanchonete.utils.URL_CONSTANTS_TEST;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LancheMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static LancheDto dto(String nome) {

        LancheDto clienteDtoMock = LancheDto.builder()
                .nome(nome)
                .valor(new BigDecimal(22.5).toString())
                .valorTotal(new BigDecimal(22.5).toString())
                .categoria(null)
                .ingredientesLanche(null)
                .build();
        return clienteDtoMock;
    }

    public static Lanche by(String nome) {
        Lanche clienteDtoMock = new Lanche();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setValorTotal(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setIngredientesLanche(null);
        return clienteDtoMock;
    }


    public LancheDto LANCHE(String nome, CategoriaDto categorialanche, 
    List<IngredienteDto> ingredientes) {
        // SAVE
        LancheDto lanche = (LancheDto) LancheMock.dto(nome);
        lanche.categoria = categorialanche;
        lanche.ingredientesLanche = ingredientes;

        BigDecimal valorCalculo = BigDecimal.ZERO;

        for (IngredienteDto ingredienteDto : ingredientes)
            valorCalculo = new BigDecimal(ingredienteDto.valor).add(valorCalculo);

        lanche.valor = valorCalculo.toString();

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(lanche, null), Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        lanche = ObjectMapperUtils.jsonTo(json, LancheDto.class);

        assertNotNull(lanche.codigo);

        return lanche;
    }
}