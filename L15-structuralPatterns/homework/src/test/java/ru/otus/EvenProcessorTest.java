package ru.otus;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorThrowExceptionEverySec;
import java.time.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EvenProcessorTest {

    @Test
    void testEvenProcessor() {
        Message message = new Message.Builder(1)
                .field11("field11")
                .field12("field12")
                .build();
        var processor = new ProcessorThrowExceptionEverySec(() -> LocalDateTime.ofEpochSecond(2, 0, ZoneOffset.ofHours(0)));
        assertThatThrownBy(() -> processor.process(message)).isInstanceOf(RuntimeException.class);
    }
}