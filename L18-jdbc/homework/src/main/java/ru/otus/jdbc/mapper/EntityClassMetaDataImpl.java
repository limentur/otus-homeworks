package ru.otus.jdbc.mapper;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class EntityClassMetaDataImpl <T> implements EntityClassMetaData{

    private final Class<T> entityClass;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        return entityClass.getConstructor();
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(entityClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow(()->new RuntimeException("Не найдено поле id")
                );
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(entityClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        Field idField = getIdField();
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.equals(idField))
                .collect(Collectors.toList());
    }
}
