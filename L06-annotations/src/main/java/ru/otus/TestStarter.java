package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestStarter extends TestStarterForFieldsExtention {

    public TestStarter(TestSet set) {
        parseAndRun(set);
        showTestStats();
    }

    private void parseAndRun(Object object){
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

    private void showTestStats(){
        logger.info("Tests passed {}", testPassed);
        logger.info("Tests failed {}", testFailed);
    }
}
