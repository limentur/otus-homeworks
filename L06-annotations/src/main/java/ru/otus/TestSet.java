package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestSet {

    private final String stringIdentifier;

    private static final Logger logger = LoggerFactory.getLogger(TestSet.class);

    public TestSet(String stringIdentifier) {
        this.stringIdentifier = stringIdentifier;
    }

    @Before
    public void testWithBeforeCall(){
        logger.info("Before call {}", stringIdentifier);
    }

    @After
    public void testWithAfterCall(){
        logger.info("After call {}",stringIdentifier);
    }

    @Test
    public void testMath(){
        logger.info("Math tested {}",stringIdentifier);
    }

    @Test
    public void testString(){
        logger.info("String tested {}",stringIdentifier);
    }

    @Test
    public void testWithException() throws Exception {
        logger.info("lets threwException in {}",stringIdentifier);
        throw new Exception();
        }
    }

