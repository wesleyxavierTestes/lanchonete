package com.lanchonete.apllication.configurations;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class MoneyConverter {

    public static class Deserialize extends JsonDeserializer<BigDecimal> {

        @Override
        public BigDecimal deserialize(JsonParser jp, DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {

            try {
                String dateAsString = jp.getText();
                if (dateAsString == null) {
                    return null;
                } else {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator(',');
                    symbols.setDecimalSeparator('.');
                    String pattern = "#,###,###,##0.0#";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
                    decimalFormat.setParseBigDecimal(true);
                    BigDecimal convert = (BigDecimal)decimalFormat.parse(dateAsString);
                    return convert;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}