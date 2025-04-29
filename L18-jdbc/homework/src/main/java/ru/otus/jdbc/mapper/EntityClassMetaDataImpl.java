package ru.otus.jdbc.mapper;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class EntityClassMetaDataImpl <T> implements EntityClassMetaData{
    private final Class<T> entityClass;
    private final String simpleName;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> entityClass) throws NoSuchMethodException {
        this.entityClass = entityClass;
        this.simpleName = entityClass.getSimpleName().toLowerCase();
        this.constructor = entityClass.getConstructor();
        this.allFields = List.of(entityClass.getDeclaredFields());
        this.idField = setIdField();
        this.fieldsWithoutId = allFields.stream()
                .filter(f -> !f.equals(idField))
                .collect(Collectors.toList());
    }

    private Field setIdField() {
        return Arrays.stream(entityClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow(()->new RuntimeException("Не найдено поле id")
                );
    }

    @Override
    public String getName() {
        return simpleName;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
