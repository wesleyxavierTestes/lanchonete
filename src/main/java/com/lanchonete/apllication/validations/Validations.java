package com.lanchonete.apllication.validations;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.el.ELException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;

public final class Validations {

    private static Validations instance;

    private boolean valid;
    private List<CustomErro> erros = new ArrayList<>();

    public static Validations get() {
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

    private static <T> Validations Erro(final T entity) throws Exception {
        return Erro(entity, "");
    }

    private static <T> Validations Erro(final T entity, String name) throws Exception {
        if (!Objects.nonNull(entity)) {
            throw new Exception("item Nulo");
        }
        if (!Objects.nonNull(Validations.instance)) {
            final List<CustomErro> erros = new ArrayList<>();
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.usingContext().getValidator();

            Class<?> cla = entity.getClass();
            List<Field> declaredFields = Arrays.asList(cla.getDeclaredFields());
            for (Field field : declaredFields) {
                String fieldNameEntity = field.getName();
                if ("ANNOTATION".equals(fieldNameEntity))
                    break;
                final Class<?> fieldType = field.getType();
                final Set<ConstraintViolation<T>> constrains = validator.validateProperty(entity, fieldNameEntity);
                if (!constrains.isEmpty()) {
                    constrains.stream().forEach(c -> {
                        final CustomErro error = new CustomErro();
                        try {
                            boolean valid = Objects.nonNull(name) && !name.isEmpty();
                            error.setProperty(valid ? name + "." + fieldNameEntity : fieldNameEntity);
                            error.setMessage(c.getMessage());
                            erros.add(error);
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    });
                } else if (!fieldType.isPrimitive() && !field.isEnumConstant()) {
                    Field listField = entity.getClass().getDeclaredField(fieldNameEntity);
                    listField.setAccessible(true);
                    if (fieldType.isArray() || field.getGenericType().getTypeName().contains("java.util")) {

                        List<Object> fs = (List<Object>) listField.get(entity);
                        int count = 0;
                        for (Object fi : fs) {
                            List<CustomErro> erros2 = Erro(fi, fieldNameEntity + "." + count).erros;
                            erros.addAll(erros2);
                            count++;
                        }
                    } else {
                        Object fs = (Object) listField.get(entity);
                        if (!(fs instanceof String) && Objects.nonNull(fs)) {
                            List<CustomErro> erros2 = Erro(fs, fieldNameEntity).erros;
                            erros.addAll(erros2);
                        }
                    }
                }

            }

            Validations.instance = new Validations(erros.isEmpty(), erros);
        }

        return Validations.instance;
    }

    private Validations(final boolean valid, final List<CustomErro> erros) {
        this.valid = valid;
        this.erros = erros;
    }

    public static <T> Validations by(T entity) {
        try {
            List<CustomErro> erros = Erro(entity).getErros();
            return byCompete(erros);
        } catch (Exception e) {
            return byCompeteException(e.getMessage());
        }
    }

    private static Validations byCompete(List<CustomErro> erros) {
        Validations.instance = new Validations(erros.isEmpty(), erros);
        return Validations.instance;
    }

    private static Validations byCompeteException(String msg) {
        List<CustomErro> erros = new ArrayList<>();
        erros.add(new CustomErro("error", msg));
        Validations.instance = new Validations(erros.isEmpty(), erros);
        return Validations.instance;
    }
}