package com.rm.ekapi.casetwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FileStorageServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        fileStorageService = new FileStorageService(objectMapper);
    }

    @Test
    public void testLoadShoppingItems() {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1))
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
    public void testSaveShoppingItems() {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );

        fileStorageService.saveShoppingItems(mockItems);

        Path path = Paths.get("shoppingList.txt");
        assertTrue(Files.exists(path));
        byte[] bytes = Files.readAllBytes(path);
        List<ShoppingItem> loadedItems = objectMapper.readValue(bytes, new TypeReference<List<ShoppingItem>>() {});

        assertEquals(mockItems.size(), loadedItems.size());
        assertEquals(mockItems.get(0).getObjectName(), loadedItems.get(0).getObjectName());
        assertEquals(mockItems.get(1).getObjectName(), loadedItems.get(1).getObjectName());

        Files.delete(path);
    }
}
