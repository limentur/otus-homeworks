package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String... args){

        TestSet firstSet = new TestSet("firstSet");

        TestSet secondSet = new TestSet("secondSet");

        TestSet thirdSet = new TestSet("thirdSet");

        logger.info("------------------");
        logger.info("FirstSet");
        logger.info("------------------");
        new TestStarter(firstSet);
        logger.info("------------------");

        logger.info("SecondSet");
        logger.info("------------------");
        new TestStarter(secondSet);
        logger.info("------------------");

        logger.info("ThirdSet");
        logger.info("------------------");
        new TestStarter(thirdSet);
        logger.info("------------------");
    }
}
