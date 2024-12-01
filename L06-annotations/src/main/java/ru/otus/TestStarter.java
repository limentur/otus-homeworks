package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TestStarter {
    private static final Logger logger = LoggerFactory.getLogger(TestStarter.class);
    private static Method beforeMethod;
    private static Method afterMethod;

    public TestStarter() {
    }

    public static void parseAndRun(Object object) throws InvocationTargetException, IllegalAccessException {
        findBeforeAndAfterAnnotations(object.getClass());

        for (var method : object.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(Test.class)){
                logger.info("Invoking {}", method.getName());
                beforeMethod.invoke(object);
                method.invoke(object);
                afterMethod.invoke(object);
                logger.info("-------------");
            }
        }
    }

    private static void findBeforeAndAfterAnnotations(Class<?> t){
        for (var method : t.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)){
                beforeMethod = method;
            }
            if (method.isAnnotationPresent(After.class)){
                afterMethod = method;
            }
        }
    }
}
