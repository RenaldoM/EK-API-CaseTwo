package com.rm.ekapi.casetwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingListServiceTest {

    private FileStorageService fileStorageService;
    private ShoppingListService shoppingListService;

    @BeforeEach
    public void setUp() {
        fileStorageService = Mockito.mock(FileStorageService.class);
        shoppingListService = new ShoppingListService(fileStorageService);
    }

    @Test
    public void testGetAllItems() {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString()),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString())
        );
        when(fileStorageService.loadShoppingItems()).thenReturn(mockItems);

        List<ShoppingItem> items = shoppingListService.getAllItems();

        assertEquals(mockItems.size(), items.size());
    }


}
