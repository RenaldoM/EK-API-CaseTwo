package com.rm.ekapi.casetwo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingitems")
public class ShoppingListController {

    private ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingItem>> getAllItems() {
        return ResponseEntity.ok(shoppingListService.getAllItems());
    }

    @GetMapping("/{objectName}")
    public ResponseEntity<ShoppingItem> getItem(@PathVariable String objectName) {
        return shoppingListService.getItem(objectName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShoppingItem> createItem(@Valid @RequestBody ShoppingItem shoppingItem) {
        shoppingListService.createItem(shoppingItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingItem);
    }

    @PutMapping("/{objectName}")
    public ResponseEntity<ShoppingItem> updateItem(@PathVariable String objectName, @Valid @RequestBody ShoppingItem shoppingItem) {
        if (shoppingItem.getQuantity() == 0) {
            return ResponseEntity.badRequest().build();
        }
        shoppingListService.updateItem(objectName, shoppingItem);
        return ResponseEntity.ok(shoppingItem);
    }

    @DeleteMapping("/{objectName}")
    public ResponseEntity<Void> deleteItem(@PathVariable String objectName) {
        shoppingListService.deleteItem(objectName);
        return ResponseEntity.noContent().build();
    }
}
