package ru.otus.jdbc.mapper;

import java.lang.reflect.*;
import java.util.stream.*;

public record EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) implements EntitySQLMetaData {

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + entityClassMetaData.getName() +
                " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        var fields = entityClassMetaData.getFieldsWithoutId();

        String columnNames = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        String placeholders = fields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO " + entityClassMetaData.getName() +
                " (" + columnNames + ") VALUES (" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        var fields = entityClassMetaData.getFieldsWithoutId();

        String setClause = fields.stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        return "UPDATE " + entityClassMetaData.getName() +
                " SET " + setClause +
                " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }
}
