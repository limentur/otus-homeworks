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

        try {
            logger.info("------------------");
            logger.info("FirstSet");
            logger.info("------------------");
            TestStarter.parseAndRun(firstSet);
            logger.info("------------------");
            logger.info("SecondSet");
            logger.info("------------------");
            TestStarter.parseAndRun(secondSet);
            logger.info("------------------");
            logger.info("ThirdSet");
            logger.info("------------------");
            TestStarter.parseAndRun(thirdSet);

        } catch (InvocationTargetException | IllegalAccessException ignored) {

        }
    }
}
