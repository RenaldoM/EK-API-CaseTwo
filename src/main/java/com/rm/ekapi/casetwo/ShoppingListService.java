package com.rm.ekapi.casetwo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private FileStorageService fileStorageService;

    public ShoppingListService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public List<ShoppingItem> getAllItems() {
        return fileStorageService.loadShoppingItems();
    }

    public Optional<ShoppingItem> getItem(String objectName) {
        return getAllItems().stream()
                .filter(item -> item.getObjectName().equalsIgnoreCase(objectName))
                .findFirst();
    }

    public ShoppingItem createItem(ShoppingItem shoppingItem) {
        List<ShoppingItem> shoppingItems = getAllItems();
        Optional<ShoppingItem> existingItem = getItem(shoppingItem.getObjectName());

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + shoppingItem.getQuantity());
        } else {
            shoppingItem.setCreatedDate(LocalDateTime.now());
            shoppingItems.add(shoppingItem);
        }

        fileStorageService.saveShoppingItems(shoppingItems);
        return shoppingItem;
    }

    public ShoppingItem updateItem(String objectName, ShoppingItem shoppingItem) {
        List<ShoppingItem> shoppingItems = getAllItems();
        shoppingItems = shoppingItems.stream()
                .map(item -> item.getObjectName().equalsIgnoreCase(objectName) ? shoppingItem : item)
                .collect(Collectors.toList());

        fileStorageService.saveShoppingItems(shoppingItems);
        return shoppingItem;
    }

    public void deleteItem(String objectName) {
        List<ShoppingItem> shoppingItems = getAllItems();
        shoppingItems.removeIf(item -> item.getObjectName().equalsIgnoreCase(objectName));

        fileStorageService.saveShoppingItems(shoppingItems);
    }
}
