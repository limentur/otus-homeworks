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
    private Method beforeMethod;
    private Method afterMethod;
    private int testPassed;
    private int testFailed;

    public TestStarter() {
    }

    public void parseAndRun(Object object){
        findBeforeAndAfterAnnotations(object.getClass());

        for (var method : object.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(Test.class)){
                invokeMethodAnnotations(method,object);
            }
        }
    }

    private void findBeforeAndAfterAnnotations(Class<?> t){
        for (var method : t.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)){
                beforeMethod = method;
            }
            if (method.isAnnotationPresent(After.class)){
                afterMethod = method;
            }
        }
    }

    private void invokeMethodAnnotations(Method method, Object object) {
        try {
            logger.info("Invoking {}", method.getName());
            beforeMethod.invoke(object);
            method.invoke(object);
            testPassed+=1;
            afterMethod.invoke(object);
            logger.info("-------------");
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("Exception {}, in {}", e.getCause(), method.getName());
            testFailed+=1;
        }
    }

    public void showTestStats(){
        logger.info("Tests passed {}", testPassed);
        logger.info("Tests failed {}", testFailed);
    }
}
