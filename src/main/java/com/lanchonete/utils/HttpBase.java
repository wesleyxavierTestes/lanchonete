package com.lanchonete.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public final class HttpBase {

    private HttpBase() {
    }

    public static <T> ResponseEntity<T> HttpGet(String url, Class<T> type) {
        return HttpGet(url, type, null);
    }

    public static <T> ResponseEntity<T> HttpGet(String url, Class<T> type, String uriVariables) {
        RestTemplate http = new RestTemplate();

        ResponseEntity<T> response = http.getForEntity(url, type, uriVariables);

        return response;
    }

}