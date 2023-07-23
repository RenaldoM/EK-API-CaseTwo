package com.rm.ekapi.casetwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileStorageService {

    private static final String FILE_NAME = "shoppingList.txt";
    private ObjectMapper objectMapper = new ObjectMapper();

    public FileStorageService(ObjectMapper objectMapper) {
        objectMapper = objectMapper;
    }

    public List<ShoppingItem> loadShoppingItems() {
        try {
            Path path = Paths.get(FILE_NAME);
            if (Files.exists(path)) {
                return objectMapper.readValue(Files.readAllBytes(path), new TypeReference<List<ShoppingItem>>() {});
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load shopping items", e);
        }
        return List.of();
    }

    public void saveShoppingItems(List<ShoppingItem> items) {
        try {
            Files.write(Paths.get(FILE_NAME), objectMapper.writeValueAsBytes(items));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save shopping items", e);
        }
    }
}
