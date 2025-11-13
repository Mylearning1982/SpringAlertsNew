// java
package com.openclassroom.safteynetalertsrefactor.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JSONFileReaderRepository {

    private static final Logger log = LoggerFactory.getLogger(JSONFileReaderRepository.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String classpathResource;
    private final Path writePath;

    public JSONFileReaderRepository(
            @Value("${app.data.classpath-resource:data.json}") String classpathResource,
            @Value("${app.data.write-path:test/updated.json}") String writePathStr
    ) {
        this.classpathResource = classpathResource;
        this.writePath = Path.of(writePathStr);
    }

    // Read JSON from file (prefer external writePath, otherwise classpath resource)
    public JsonNode readJson() {
        try {
            if (Files.exists(writePath)) {
                try (InputStream in = Files.newInputStream(writePath)) {
                    return objectMapper.readTree(in);
                }
            }
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(classpathResource)) {
                if (in == null) {
                    return objectMapper.createObjectNode();
                }
                return objectMapper.readTree(in);
            }
        } catch (IOException e) {
            log.error("Error reading JSON, returning empty object node", e);
            return objectMapper.createObjectNode();
        }
    }

    // Write JsonNode to file
    public void writeJson(JsonNode root) {
        try {
            Path parent = writePath.toAbsolutePath().getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writePath.toFile(), root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> readList(String arrayName, Class<T> elementType) {
        JsonNode root = readJson();
        JsonNode arrayNode = root.path(arrayName);
        if (!arrayNode.isArray() || arrayNode.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.convertValue(
                arrayNode,
                objectMapper.getTypeFactory().constructCollectionType(List.class, elementType)
        );
    }

    public <T> void writeList(String arrayName, List<T> items) {
        JsonNode root = readJson();
        ObjectNode objectNode = root.isObject() ? (ObjectNode) root : objectMapper.createObjectNode();
        objectNode.set(arrayName, objectMapper.valueToTree(items));
        writeJson(objectNode);
    }
}
