package ru.otus.dataprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws Exception {
        // читает файл, парсит и возвращает результат
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            return mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (IOException e) {
            throw new Exception("Cannot load file resource", e);
        }
    }
}
