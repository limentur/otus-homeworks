package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String... args){

        TestSet firstSet = new TestSet("firstSet");

        TestSet secondSet = new TestSet("secondSet");

        TestSet thirdSet = new TestSet("thirdSet");

        TestStarter starter = new TestStarter();

        logger.info("------------------");
        logger.info("FirstSet");
        logger.info("------------------");
        starter.parseAndRun(firstSet);
        logger.info("------------------");

        logger.info("SecondSet");
        logger.info("------------------");
        starter.parseAndRun(secondSet);
        logger.info("------------------");

        logger.info("ThirdSet");
        logger.info("------------------");
        starter.parseAndRun(thirdSet);
        logger.info("------------------");

        starter.showTestStats();
    }
}
