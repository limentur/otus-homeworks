package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorThrowExceptionEverySec implements Processor {

        @Override
        public Message process(Message message) throws EvenSecondException {
            int currentSecond = getCurrentSecond();

            if (isEvenSecond(currentSecond)) {
                throw new EvenSecondException("Текущая секунда " + currentSecond + " является четной!");
            }
            return message;
        }

        private int getCurrentSecond() {
            return LocalDateTime.now().getSecond();
        }

        private Boolean isEvenSecond(int second){
            return (second % 2) == 0;
        }
}
