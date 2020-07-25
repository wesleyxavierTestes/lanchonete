package com.lanchonete.apllication.validations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomErro {
    public String property;
    public String message;
}
