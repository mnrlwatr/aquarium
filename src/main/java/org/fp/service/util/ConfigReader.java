package org.fp.service.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class ConfigReader {
    private ConfigReader() {
    }

    public static <T> T readConfig(String filePath, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        try {
            return objectMapper.readValue(new File(filePath),clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
