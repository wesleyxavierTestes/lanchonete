package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioItemDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.exceptions.RegraNegocioException;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.Cardapio;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
import com.lanchonete.mocks.pages.BebidaUtilsPageMock;
import com.lanchonete.mocks.pages.ComboUtilsPageMock;
import com.lanchonete.mocks.pages.LancheUtilsPageMock;
import com.lanchonete.mocks.pages.ProdutoUtilsPageMock;
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
public class CardapioMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static CardapioDto dto(String nome) {
        CardapioDto clienteDtoMock = CardapioDto.builder().nome(nome).build();
        return clienteDtoMock;
    }

    public static Cardapio by(String nome) {
        Cardapio clienteDtoMock = new Cardapio();
        clienteDtoMock.setNome(nome);
        return clienteDtoMock;
    }

    public CardapioDto CARDAPIO(String nome, List<ProdutoDto> produtos, List<LancheDto> lanches,
            List<ComboDto> combos) {
        CardapioDto cardapio = (CardapioDto) CardapioMock.dto(nome);
        cardapio.itensDisponiveis = new ArrayList<>();
        CardapioItemDto produto = null;

        String urlProduto = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoList + "/?page=1", port);
        ResponseEntity<ProdutoUtilsPageMock> responseProduto = restTemplate.getForEntity(urlProduto,
                ProdutoUtilsPageMock.class);
        ProdutoUtilsPageMock pageProduto = responseProduto.getBody();

        String urlLanche = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheList + "/?page=1", port);
        ResponseEntity<LancheUtilsPageMock> responseLanche = restTemplate.getForEntity(urlLanche,
                LancheUtilsPageMock.class);
        LancheUtilsPageMock pageLanche = responseLanche.getBody();

        String urlCombo = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboList + "/?page=1", port);
        ResponseEntity<ComboUtilsPageMock> responseCombo = restTemplate.getForEntity(urlCombo,
                ComboUtilsPageMock.class);
        ComboUtilsPageMock pageCombo = responseCombo.getBody();

        String urlBebida = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.BebidaList + "/?page=1", port);
        ResponseEntity<BebidaUtilsPageMock> responseBebida = restTemplate.getForEntity(urlBebida,
                BebidaUtilsPageMock.class);
        BebidaUtilsPageMock pageBebida = responseBebida.getBody();
        
        for (int i = 0; i < 30; i++) {
            try {
                if (i % 3 == 0) {
                    int indexOutros = new Random().nextInt(pageProduto.content.size());
                    produto = Mapper.map(pageProduto.content.get(indexOutros), CardapioItemDto.class);
                    produto.tipoProduto = EnumTipoProduto.Outros;
                } else if (i % 4 == 0) {
                    int indexLanche = new Random().nextInt(pageLanche.content.size());
                    produto = Mapper.map(pageLanche.content.get(indexLanche), CardapioItemDto.class);
                    produto.tipoProduto = EnumTipoProduto.Lanche;
                } else if (i % 5 == 0) {
                    int indexCombo = new Random().nextInt(pageCombo.content.size());
                    produto = Mapper.map(pageCombo.content.get(indexCombo), CardapioItemDto.class);
                    produto.tipoProduto = EnumTipoProduto.Combo;
                } else {
                    int indexBebida = new Random().nextInt(pageBebida.content.size());
                    produto = Mapper.map(pageBebida.content.get(indexBebida), CardapioItemDto.class);
                    produto.tipoProduto = EnumTipoProduto.Bebida;
                }
                cardapio.itensDisponiveis.add(produto);
            } catch (Exception e) {
                throw new RegraNegocioException("Exceção desconhecida: " + e.getMessage());
            }
        }

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CardapioSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(cardapio, null),
                Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        cardapio = ObjectMapperUtils.jsonTo(json, CardapioDto.class);

        assertNotNull(cardapio);

        return cardapio;
    }

}