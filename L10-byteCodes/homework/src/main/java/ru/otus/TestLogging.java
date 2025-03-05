package ru.otus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {
    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Override
    @Log
    public void calculation(int param1) {
       logger.info("Calculation(int param1)");
    }

    @Override
    public void calculation(int param1, int param2) {
        logger.info("calculation(int param1, int param2)");
    }

    @Override
    @Log
    public void calculation(int param1, int param2, String param3) {
        logger.info("calculation(int param1, int param2, String param3)");
    }

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
