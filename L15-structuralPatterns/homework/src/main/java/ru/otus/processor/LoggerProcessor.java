package ru.otus.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;

public class LoggerProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(LoggerProcessor.class);

    private final Processor processor;

    public LoggerProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) throws EvenSecondException {
        logger.info("log processing message:{}", message);
        return processor.process(message);
    }
}
