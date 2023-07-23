package com.rm.ekapi.casetwo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileStorageServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        fileStorageService = new FileStorageService(objectMapper);
    }

    @Test
    public void testLoadShoppingItems() throws IOException {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString()),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString())
        );
        Path path = Paths.get("shoppingList.txt");
        Files.write(path, objectMapper.writeValueAsBytes(mockItems));

        List<ShoppingItem> loadedItems = fileStorageService.loadShoppingItems();

        assertEquals(mockItems.size(), loadedItems.size());
        assertEquals(mockItems.get(0).getObjectName(), loadedItems.get(0).getObjectName());
        assertEquals(mockItems.get(1).getObjectName(), loadedItems.get(1).getObjectName());

        Files.delete(path);
    }

    @Test
    public void testSaveShoppingItems() throws IOException {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString()),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString())
        );

        fileStorageService.saveShoppingItems(mockItems);

        Path path = Paths.get("shoppingList.txt");
        assertTrue(Files.exists(path));
        byte[] bytes = Files.readAllBytes(path);
        List<ShoppingItem> loadedItems = objectMapper.readValue(bytes, new TypeReference<List<ShoppingItem>>() {
        });

        assertEquals(mockItems.size(), loadedItems.size());
        assertEquals(mockItems.get(0).getObjectName(), loadedItems.get(0).getObjectName());
        assertEquals(mockItems.get(1).getObjectName(), loadedItems.get(1).getObjectName());

        Files.delete(path);
    }
}
