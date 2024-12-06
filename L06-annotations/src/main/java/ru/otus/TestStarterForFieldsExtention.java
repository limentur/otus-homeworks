package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TestStarterForFieldsExtention {
    protected static final Logger logger = LoggerFactory.getLogger(TestStarterForFieldsExtention.class);
    protected Method beforeMethod;
    protected Method afterMethod;
    protected int testPassed;
    protected int testFailed;
}
