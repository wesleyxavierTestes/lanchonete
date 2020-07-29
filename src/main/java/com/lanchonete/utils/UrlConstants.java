package com.lanchonete.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public final class UrlConstants {

    private UrlConstants() { }

    public static String Hora =String.format("%s%s%s", LocalDate.now().getDayOfMonth(), LocalTime.now().getHour(), LocalTime.now().getMinute());

    public final static String getViaCep(String cep) {
        return String.format("https://viacep.com.br/ws/$s/json/", cep);
    }
}