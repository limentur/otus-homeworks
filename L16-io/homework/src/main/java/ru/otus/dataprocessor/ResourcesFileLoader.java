package ru.otus.dataprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    String fileName;
    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        // читает файл, парсит и возвращает результат
        return new ObjectMapper().readValue(
                new String(Files.readAllBytes(Paths.get("src/test/resources/inputData.json"))),
                new TypeReference<>() {}
        );
    }
}
