package ru.otus;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorThrowExceptionEverySec;
import ru.otus.processor.homework.SecInf;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

class EvenProcessorTest {
    @Test
    void testEvenProcessor() throws EvenSecondException {
        int seconds = 0;
        Message message = new Message.Builder(1)
                .field11("field11")
                .field12("field12")
                .build();
        Message expected = new Message.Builder(2)
                .field11("field11")
                .field12("field12")
                .build();

        ProcessorThrowExceptionEverySec processor = new ProcessorThrowExceptionEverySec();

        while (!(processor.getCurrentSecond() % 2 == 0)){
            if (processor.getCurrentSecond() % 2 == 0) {
                assertThatThrownBy(() -> processor.process(message)).isInstanceOf(RuntimeException.class);
            } else {
                var actual = processor.process(message);
                assertThat(actual.getField11()).isEqualTo(expected.getField11());
                assertThat(actual.getField12()).isEqualTo(expected.getField12());
            }
        }

    }
}