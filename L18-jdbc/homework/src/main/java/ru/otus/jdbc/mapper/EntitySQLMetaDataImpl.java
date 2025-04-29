package ru.otus.jdbc.mapper;

import java.lang.reflect.*;
import java.util.stream.*;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    public EntityClassMetaData<?> entityClassMetaData;
    public String select;
    public String selectById;
    public String insert;
    public String update;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.select = "SELECT "+formColumnNames()+" FROM " + entityClassMetaData.getName();
        this.selectById = "SELECT "+formColumnNames()+" FROM " + entityClassMetaData.getName() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
        this.insert = formInsertSql();
        this.update = formUpdateSql();

    }

    @Override
    public String getSelectAllSql() {
        return select;
    }

    @Override
    public String getSelectByIdSql() {
        return selectById;
    }

    @Override
    public String getInsertSql() {
        return insert;
    }

    @Override
    public String getUpdateSql() {
        return update;
    }

    private String formInsertSql() {
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

    public String formUpdateSql() {
        var fields = entityClassMetaData.getFieldsWithoutId();

        String setClause = fields.stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        return "UPDATE " + entityClassMetaData.getName() +
                " SET " + setClause +
                " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    private String formColumnNames() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }
}
