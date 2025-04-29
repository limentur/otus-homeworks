package ru.otus.cachehw;

import org.flywaydb.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.*;
import ru.otus.core.sessionmanager.*;
import ru.otus.crm.datasource.*;
import ru.otus.crm.model.*;
import ru.otus.crm.service.*;
import ru.otus.mapper.*;

import javax.sql.*;
import java.util.stream.*;

public class HWCacheDemo {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<String, Integer> cache = new MyCache<>();

        // Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        // Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient  = null;
        try {
            entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        EntitySQLMetaData entitySQLMetaDataClient  = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(
                dbExecutor, entitySQLMetaDataClient,entityClassMetaDataClient);
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);

        IntStream.range(1, 10).forEach(val -> {
            dbServiceClient.saveClient(new Client(String.valueOf(val)));
            cache.put(String.valueOf(val),val);
        }
        );

        speedTest(dbServiceClient,cache);
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }

    public void speedTest(DbServiceClientImpl service, HwCache<String,Integer> cache){
        long startTime = System.nanoTime();
        IntStream.range(1, 10).forEach(val -> logger.info("value fromDb : {}", service.getClient(val)));
        long endTime = System.nanoTime();
        long durationDb = endTime - startTime;

        startTime = System.nanoTime();
        IntStream.range(1, 10).forEach(val -> logger.info("value from cache : {}", cache.get(String.valueOf(val))));
        endTime = System.nanoTime();
        long durationCache = endTime - startTime;

        System.out.println("Время работы с БД: " + durationDb + " наносекунд");
        System.out.println("Время работы с кешем: " + durationCache + " наносекунд");
    }
}
