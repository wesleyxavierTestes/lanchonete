package com.lanchonete.utils;

import java.util.Objects;

import org.modelmapper.ModelMapper;

public final class ModelMapperUtils {
    private static ModelMapper instance;
    
    private ModelMapperUtils() {
        super();
    }
    
    public static ModelMapper getInstance() {
        if (!Objects.nonNull(ModelMapperUtils.instance)) {
            ModelMapperUtils.instance = new ModelMapper();
        }
        return ModelMapperUtils.instance;
    }
}