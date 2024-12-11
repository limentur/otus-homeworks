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


    public TestStarter(TestSet set) {
        parseAndRun(set);
    }

    private void parseAndRun(Object object){
        Method beforeMethod = findBeforeAnnotations(object.getClass());
        Method afterMethod = findAfterAnnotations(object.getClass());
        int testPassed = 0;
        int testFailed = 0;
        for (var method : object.getClass().getDeclaredMethods()){
            if (method.isAnnotationPresent(Test.class)){
                if((invokeMethodAnnotations(method,object,beforeMethod, afterMethod) == 1)){
                    testPassed+=1;
                }
                else {
                    testFailed +=1;
                }
            }
        }
        showTestStats(testPassed,testFailed);
    }

    private Method findBeforeAnnotations(Class<?> t){
        for (var method : t.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)){
                return method;
            }
        }
        return null;
    }

    private Method findAfterAnnotations(Class<?> t){
        for (var method : t.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)){
                 return method;
            }
        }
        return null;
    }

    private int invokeMethodAnnotations(Method method, Object object,Method beforeMethod, Method afterMethod) {
        try {
            logger.info("Invoking {}", method.getName());
            beforeMethod.invoke(object);
            method.invoke(object);
            afterMethod.invoke(object);
            logger.info("-------------");
            return 1;
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("Exception {}, in {}", e.getCause(), method.getName());
            return 0;
        }
    }

    private void showTestStats(int testPassed,int testFailed){
        logger.info("Tests passed {}", testPassed);
        logger.info("Tests failed {}", testFailed);
    }
}
