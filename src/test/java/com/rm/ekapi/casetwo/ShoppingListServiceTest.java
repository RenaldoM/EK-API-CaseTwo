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
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );
        when(fileStorageService.loadShoppingItems()).thenReturn(mockItems);

        List<ShoppingItem> items = shoppingListService.getAllItems();

        assertEquals(mockItems.size(), items.size());
    }

    @Test
    public void testGetItem() {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(fileStorageService.loadShoppingItems()).thenReturn(Arrays.asList(mockItem));

        ShoppingItem item = shoppingListService.getItem("Apple").get();

        assertEquals(mockItem.getObjectName(), item.getObjectName());
    }

    @Test
    public void testCreateItem() {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(fileStorageService.loadShoppingItems()).thenReturn(Arrays.asList(mockItem));

        ShoppingItem newItem = new ShoppingItem("Banana", 5, "User2", null, LocalDateTime.now().plusDays(1));
        shoppingListService.createItem(newItem);

        verify(fileStorageService, times(1)).saveShoppingItems(any());
    }

    @Test
    public void testUpdateItem() {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(fileStorageService.loadShoppingItems()).thenReturn(Arrays.asList(mockItem));

        ShoppingItem updatedItem = new ShoppingItem("Apple", 5, "User1", null, LocalDateTime.now().plusDays(1));
        shoppingListService.updateItem("Apple", updatedItem);

        verify(fileStorageService, times(1)).saveShoppingItems(any());
    }

    @Test
    public void testDeleteItem() {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(fileStorageService.loadShoppingItems()).thenReturn(Arrays.asList(mockItem));

        shoppingListService.deleteItem("Apple");

        verify(fileStorageService, times(1)).saveShoppingItems(any());
    }
}
