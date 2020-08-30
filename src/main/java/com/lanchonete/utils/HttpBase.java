package com.lanchonete.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public final class HttpBase {

    public <T> ResponseEntity<T> HttpGet(String url, Class<T> type) {
        return HttpGet(url, type, null);
    }

    public <T> ResponseEntity<T> HttpGet(String url, Class<T> type, String uriVariables) {
        RestTemplate http = new RestTemplate();

        ResponseEntity<T> response = http.getForEntity(url, type, uriVariables);

        return response;
    }

    public static <T> ResponseEntity<T> getParameterResponseEntity(String url, String parametro, Class<T> tipo) {
        RestTemplate http = new RestTemplate();
        ResponseEntity<T> consultado = http.getForEntity(url, tipo, parametro);

        if (consultado.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        if (consultado.getBody() == null) return new ResponseEntity(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity(consultado.getBody(), HttpStatus.OK);

    }

    public static <T> ResponseEntity<T> getParameterResponseEntity(String url, Class<T> tipo) {
        RestTemplate http = new RestTemplate();
        ResponseEntity<T> consultado = http.getForEntity(url, tipo);

        if (consultado.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        if (consultado.getBody() == null) return new ResponseEntity(null, HttpStatus.NO_CONTENT);

        return new ResponseEntity(consultado.getBody(), HttpStatus.OK);

    }
}