package ru.otus.jdbc.mapper;

import java.sql.*;
import java.util.*;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection,entitySQLMetaData.getSelectByIdSql(),List.of(id),resultSet -> {
            try {
                T mappingClass = entityClassMetaData.getConstructor().newInstance();
                if (resultSet.next()){
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object value = resultSet.getObject(field.getName());
                            field.set(mappingClass, value);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return mappingClass;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection,entitySQLMetaData.getSelectByIdSql(),List.of(),resultSet -> {
            try {
                var result = new ArrayList<T>();
                if (resultSet.next()){
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            T mappingClass = entityClassMetaData.getConstructor().newInstance();
                            field.setAccessible(true);
                            Object value = resultSet.getObject(field.getName());
                            field.set(mappingClass, value);
                            result.add(mappingClass);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).orElse((ArrayList<T>) Collections.emptyList());
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return dbExecutor.executeStatement(connection,entitySQLMetaData.getInsertSql(),params);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        entityClassMetaData.getAllFields().forEach(field -> {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        dbExecutor.executeStatement(connection,entitySQLMetaData.getUpdateSql(),params);
    }
}
