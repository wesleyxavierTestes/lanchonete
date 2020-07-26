package com.lanchonete.apllication.validations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Validations {

    private boolean valid;
    private List<CustomErro> erros = new ArrayList<>();

    public <T> Validations by(final T entity) {
        try {
            this.erros = new ArrayList<>(); 
            this.valid = Erro(entity).erros.isEmpty();           
            return this;
        } catch (final Exception e) {
            return SetValidationException(e.getMessage());
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public List<CustomErro> getErros() {
        return erros;
    }

    /**
     * Recebe a Entity root da hierarquia
     * 
     * @param <T>
     * @param entity
     * @return
     * @throws Exception
     */
    private <T> Validations Erro(final T entity) throws Exception {
        return Erro(entity, "");
    }

    /**
     * Valida Entidade e os Objetos ou Arrays filhos E seta uma nova instancia de
     * Validations
     * 
     * @param <T>
     * @param entity     Classes com anotation do javax.validation
     * @param entityName
     * @return Lista de Validations
     * @throws Exception
     */
    private <T> Validations Erro(final T entity, final String entityName) throws Exception {
        if (!Objects.nonNull(entity))
            throw new Exception("item Nulo");
        final Validator validator = configureValidateError();
        
        Erro(entity, entityName, validator);

        return this;
    }

    /**
     * Percorre a lista de Propriedades da Entidade
     * 
     * @param <T>
     * @param entity
     * @param entityName
     * @param erros
     * @param validator
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws Exception
     */
    private <T> void Erro(final T entity, final String entityName, final Validator validator) throws NoSuchFieldException, IllegalAccessException, Exception {

        for (final Field field : getDeclaredFields(entity)) {
            String fieldName = field.getName();
            if ("ANNOTATION".equals(fieldName))
                break;

            final Set<ConstraintViolation<T>> constrains = getConstrains(entity, validator, field.getName());

            if (!constrains.isEmpty())
                setErros(entityName, fieldName, constrains);
            else if (!field.getType().isPrimitive() && !field.isEnumConstant()) {
                NestedErro(entity, field);
            }
        }
    }

    /**
     * Valida se existe Entity Filha, Object ou Array
     * 
     * @param <T>
     * @param entity
     * @param erros
     * @param field
     * @param fieldNameEntity
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws Exception
     */
    private <T> void NestedErro(final T entity, final Field field) throws NoSuchFieldException, IllegalAccessException, Exception {
        final String fieldNameEntity = field.getName();
        final Field listField = entity.getClass().getDeclaredField(fieldNameEntity);

        listField.setAccessible(true);

        if (isObjectArray(field))
            ErroArrayNested(entity, fieldNameEntity, listField);
        else if (!field.getType().isEnum())
            ErroObjectNested(entity, fieldNameEntity, listField);
    }

    /**
     * Valida se existe Entity Filha do tipo Array
     * 
     * @param <T>
     * @param entity
     * @param erros
     * @param fieldNameEntity
     * @param listField
     * @throws IllegalAccessException
     * @throws Exception
     */
    private <T> void ErroArrayNested(final T entity, final String fieldNameEntity,
            final Field listField) throws IllegalAccessException, Exception {
        final List<Object> nestedArray = (List<Object>) listField.get(entity);
        int count = 0;
        for (final Object nestedObject : nestedArray) {
            String entityName = String.format("%s.%s", fieldNameEntity, count);
            final List<CustomErro> erros2 = Erro(nestedObject, entityName).erros;
            this.erros.addAll(erros2);
            count++;
        }
    }

    /**
     * Valida se existe Entity Filha do tipo Object
     * 
     * @param <T>
     * @param entity
     * @param erros
     * @param fieldNameEntity
     * @param listField
     * @throws IllegalAccessException
     * @throws Exception
     */
    private <T> void ErroObjectNested(final T entity, final String fieldNameEntity,
            final Field listField) throws IllegalAccessException, Exception {

        final Object fs = (Object) listField.get(entity);

        if (!(fs instanceof String) && Objects.nonNull(fs)) {
            Erro(fs, fieldNameEntity);
        }
    }

    /**
     * Faz a validação e Retorna um tipo Set de erros encontrados
     */
    private <T> Set<ConstraintViolation<T>> getConstrains(final T entity, final Validator validator,
            final String fieldNameEntity) {
        final Set<ConstraintViolation<T>> constrains = validator.validateProperty(entity, fieldNameEntity);
        return constrains;
    }

    /**
     * Lista as propriedades do objeto via reflexão
     * 
     * @param <T>
     * @param entity
     * @return
     */
    private <T> List<Field> getDeclaredFields(final T entity) {
        final List<Field> declaredFields = Arrays.asList(entity.getClass().getDeclaredFields());
        return declaredFields;
    }

    /***
     * Inicia uma instancia do Validation do javax.validation usando o contexto do
     * request http atual para validação
     * 
     * @return Validator
     */
    private Validator configureValidateError() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator novoValidator = validatorFactory.usingContext().getValidator();
        return novoValidator;
    }

    /**
     * 
     * @param <T>
     * @param entityName
     * @param erros
     * @param fieldNameEntity
     * @param constrains
     */
    private <T> void setErros(final String entityName,
            final String fieldNameEntity, final Set<ConstraintViolation<T>> constrains) {
        constrains.stream().forEach(c -> {
            try {
                final boolean valid = Objects.nonNull(entityName) && !entityName.isEmpty();

                String property = valid ? String.format("%s.%s", entityName, fieldNameEntity) : fieldNameEntity;
                String message = c.getMessage();

                this.erros.add(CustomErro.builder().property(property).message(message).build());
            } catch (final Exception e) {
                System.out.print(e.getMessage());
            }
        });
    }

    /**
     * Valida se é um Objeto do tipo List/Array/Collection
     * 
     * @param field
     * @return
     */
    private boolean isObjectArray(final Field field) {
        String packageName = "java.util";
        return field.getType().isArray() || field.getGenericType().getTypeName().contains(packageName);
    }

    private Validations SetValidationException(final String msg) {
        final List<CustomErro> erros = new ArrayList<>();
        erros.add(new CustomErro("error", msg));
        this.erros = erros;
        return this;
    }
}