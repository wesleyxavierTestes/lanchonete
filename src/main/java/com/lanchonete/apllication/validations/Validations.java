package com.lanchonete.apllication.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.lanchonete.apllication.dto.cliente.ClienteDto;

public final class Validations {

    private static Validations instance;

    private boolean valid;
    private List<CustomErro> erros = new ArrayList<>();

    public static Validations get(){
        return instance;
    }

    public boolean isValid() {
        return this.valid;
    }

    public List<CustomErro> getErros() {
        List<CustomErro> errosClone = new ArrayList<>(Validations.instance.erros);
        Validations.instance = null;
        return errosClone;
    }

    public static <T> Validations Erro(final T entity) {
        if (!Objects.nonNull(Validations.instance)) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.usingContext().getValidator();
            final Set<ConstraintViolation<T>> constrains = validator.validate(entity);
            final List<CustomErro> erros = new ArrayList<>();
            constrains.stream().forEach(c -> {
                final CustomErro error = new CustomErro();
                error.setProperty(c.getPropertyPath().toString());
                error.setMessage(c.getMessage());
                erros.add(error);
            });

            Validations.instance = new Validations(erros.isEmpty(), erros);
        }

        return Validations.instance;
    }

    public Validations(final boolean valid, final List<CustomErro> erros) {
        this.valid = valid;
        this.erros = erros;
    }

    public static Validations by(ClienteDto entity) {
        List<CustomErro> erros = Erro(entity).getErros();
        List<CustomErro> erros2 = Erro(entity.endereco).getErros();
        erros.addAll(erros2.stream().map(c -> {
            c.property = "endereco." + c.property;
            return c;
        }).collect(Collectors.toList()));
        Validations.instance = new Validations(erros.isEmpty(), erros);
        return Validations.instance;
    }
}