package com.lanchonete.apllication.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseValidate {

    public boolean valid = true;

    public List<Map<String, String>> Validations;

    public BaseValidate SetValidation(String key, String value) {
        if (!Objects.nonNull(Validations))
            Validations = new ArrayList<>();
        
        Map<String,String> map = new HashMap<>();
        map.put(key, value);

        Validations.add(map);
        this.valid = false;

        return this;
    }

    public BaseValidate SetValidations(List<Map<String, String>> v) {
        if (!Objects.nonNull(Validations))
            Validations = new ArrayList<>();

        Validations.addAll(v);
        this.valid = false;
        return this;
    }

    public abstract boolean getIsValid();
}
